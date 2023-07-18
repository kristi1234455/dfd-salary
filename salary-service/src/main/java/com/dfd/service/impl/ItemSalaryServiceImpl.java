package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.*;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemSalaryMapper;
import com.dfd.service.ItemSalaryService;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.ItemSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 10:39
 */
@Service
public class ItemSalaryServiceImpl extends ServiceImpl<ItemSalaryMapper, ItemSalary> implements ItemSalaryService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;
    @Override
    public PageResult<ItemSalaryInfoVO> info(ItemSalaryInfoDTO itemSalaryInfoDTO) {
        LambdaQueryWrapper<ItemSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(itemSalaryInfoDTO.getItemUid()), ItemSalary:: getItemUid, itemSalaryInfoDTO.getItemUid())
                .likeRight(itemSalaryInfoDTO.getDeclareTime() !=null, ItemSalary:: getDeclareTime, itemSalaryInfoDTO.getDeclareTime())
                .eq(StringUtils.isNotEmpty(itemSalaryInfoDTO.getItemStage()), ItemSalary:: getItemStage, itemSalaryInfoDTO.getItemStage())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(ItemSalary :: getCreatedTime);

        Integer pageNum = itemSalaryInfoDTO.getCurrentPage();
        Integer pageSize = itemSalaryInfoDTO.getPageSize();
        List<ItemSalary> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<ItemSalaryInfoVO> list = convertToSalaryInfoVO(members);
        int size = list.size();
        //先判断pageNum(使之page <= 0 与page==1返回结果相同)
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 0 : pageSize;
        int pageStart = (pageNum - 1) * pageSize;//截取的开始位置 pageNum>=1
        int pageEnd = size < pageNum * pageSize ? size : pageNum * pageSize;//截取的结束位置
        if (size > pageNum) {
            list = list.subList(pageStart, pageEnd);
        }
        //防止pageSize出现<=0
        pageSize = pageSize <= 0 ? 1 : pageSize;
        PageResult<ItemSalaryInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<ItemSalaryInfoVO> convertToSalaryInfoVO(List<ItemSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(ItemSalary::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(ItemSalary::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<ItemSalaryInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("项目工资数据为空");
            }
            ItemSalaryInfoVO salaryInfoVO = new ItemSalaryInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(ItemSalaryAddDTO itemSalaryDTO) {
        LambdaQueryWrapper<ItemSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(itemSalaryDTO.getItemUid()), ItemSalary:: getItemUid, itemSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(itemSalaryDTO.getItemMemberUid()), ItemSalary:: getItemMemberUid, itemSalaryDTO.getItemMemberUid())
                .eq(itemSalaryDTO.getDeclareTime()!=null, ItemSalary:: getDeclareTime, itemSalaryDTO.getDeclareTime())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户项目工资数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        ItemSalary salary = new ItemSalary();
        BeanUtil.copyProperties(itemSalaryDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("项目工资数据保存失败");
        }
    }

    @Override
    public void update(ItemSalaryDTO itemSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ItemSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(itemSalaryDTO.getUid()), ItemSalary:: getUid, itemSalaryDTO.getUid())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set((itemSalaryDTO.getPostSalaryStandard()!=null), ItemSalary:: getPostSalaryStandard, itemSalaryDTO.getPostSalaryStandard())
                .set(StringUtils.isNotBlank(itemSalaryDTO.getPlanApproveFactor()), ItemSalary:: getPlanApproveFactor, itemSalaryDTO.getPlanApproveFactor())
                .set(StringUtils.isNotBlank(itemSalaryDTO.getDeclareFactor()), ItemSalary:: getDeclareFactor, itemSalaryDTO.getDeclareFactor())
                .set((itemSalaryDTO.getDeclareGrant()!=null), ItemSalary:: getDeclareGrant, itemSalaryDTO.getDeclareGrant())
                .set(StringUtils.isNotBlank(itemSalaryDTO.getItemStage()), ItemSalary:: getItemStage, itemSalaryDTO.getItemStage())
                .set((itemSalaryDTO.getDeclareTime()!=null), ItemSalary:: getDeclareTime, itemSalaryDTO.getDeclareTime())
                .set(StringUtils.isNotBlank(itemSalaryDTO.getRemarks()), ItemSalary:: getRemarks, itemSalaryDTO.getRemarks())
                .set(ItemSalary:: getUpdatedBy, currentUser.getPhone())
                .set(ItemSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("项目工资更新失败!");
        }
    }

    @Override
    public void delete(ItemSalaryDelDTO itemSalaryDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<ItemSalary> updateWrapper = new UpdateWrapper<ItemSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(itemSalaryDelDTO.getUids()), ItemSalary:: getUid, itemSalaryDelDTO.getUids())
                .set(ItemSalary:: getIsDeleted, System.currentTimeMillis())
                .set(ItemSalary:: getUpdatedBy, currentUser.getPhone())
                .set(ItemSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("项目工资删除失败!");
        }
    }
}

package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyNight;
import com.dfd.entity.User;
import com.dfd.mapper.SubsidyNightMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SubsidyNightService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyNightInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
@Service
public class SubsidyNightServiceImpl extends ServiceImpl<SubsidyNightMapper, SubsidyNight> implements SubsidyNightService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;


    @Override
    public PageResult<SubsidyNightInfoVO> info(SubsidyNightInfoDTO subsidyNightInfoDTO) {
        LambdaQueryWrapper<SubsidyNight> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyNightInfoDTO.getItemUid()), SubsidyNight:: getItemUid, subsidyNightInfoDTO.getItemUid())
                .eq(subsidyNightInfoDTO.getNightDeclareTime() !=null, SubsidyNight:: getNightDeclareTime, subsidyNightInfoDTO.getNightDeclareTime())
                .eq(SubsidyNight::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(SubsidyNight :: getCreatedTime);


        Integer pageNum = subsidyNightInfoDTO.getCurrentPage();
        Integer pageSize = subsidyNightInfoDTO.getPageSize();
        List<SubsidyNight> members = baseMapper.selectList(queryWrapper);
        members = members.stream().filter(e -> e.getNightDays() != null).collect(Collectors.toList());
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<SubsidyNightInfoVO> list = convertToNightSalaryInfoVO(members);
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
        PageResult<SubsidyNightInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<SubsidyNightInfoVO> convertToNightSalaryInfoVO(List<SubsidyNight> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(SubsidyNight::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(SubsidyNight::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<SubsidyNightInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("夜班补贴数据为空");
            }
            SubsidyNightInfoVO salaryInfoVO = new SubsidyNightInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(SubsidyNightAddDTO subsidyNightDTO) {
        LambdaQueryWrapper<SubsidyNight> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyNightDTO.getItemUid()), SubsidyNight:: getItemUid, subsidyNightDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyNightDTO.getItemMemberUid()), SubsidyNight:: getItemMemberUid, subsidyNightDTO.getItemMemberUid())
                .eq(subsidyNightDTO.getNightDays()!=null, SubsidyNight:: getNightDays, subsidyNightDTO.getNightDays())
                .likeRight(subsidyNightDTO.getNightDeclareTime()!=null, SubsidyNight:: getNightDeclareTime, subsidyNightDTO.getNightDeclareTime())
                .eq(SubsidyNight::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户夜班补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        SubsidyNight salary = new SubsidyNight();
        BeanUtil.copyProperties(subsidyNightDTO,salary);
        String uid = subsidyNightDTO.getItemUid() + subsidyNightDTO.getItemMemberUid() + DateUtil.getYM();
        salary.setUid(uid)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("夜班补贴数据保存失败");
        }
    }

    @Override
    public void update(SubsidyNightDTO subsidyNightDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyNight> updateWrapper = new LambdaUpdateWrapper<>();
        String uid = subsidyNightDTO.getItemUid() + subsidyNightDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyNightDTO.getUid()), SubsidyNight:: getUid, subsidyNightDTO.getUid())
                .eq(SubsidyNight::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(SubsidyNight:: getUid, uid)
                .set(StringUtils.isNotBlank(subsidyNightDTO.getNightDuty()), SubsidyNight:: getNightDuty, subsidyNightDTO.getNightDuty())
                .set(StringUtils.isNotBlank(subsidyNightDTO.getNightWorkContent()), SubsidyNight:: getNightWorkContent, subsidyNightDTO.getNightWorkContent())
                .set((subsidyNightDTO.getNightSubsidyStandard()!=null), SubsidyNight:: getNightSubsidyStandard, subsidyNightDTO.getNightSubsidyStandard())
                .set(subsidyNightDTO.getNightDays()!=null, SubsidyNight:: getNightDays, subsidyNightDTO.getNightDays())
                .set(subsidyNightDTO.getNightSubsidy()!=null, SubsidyNight:: getNightSubsidy, subsidyNightDTO.getNightSubsidy())
                .set((subsidyNightDTO.getNightRemarks()!=null), SubsidyNight:: getNightRemarks, subsidyNightDTO.getNightRemarks())
                .set((subsidyNightDTO.getNightDeclareTime()!=null), SubsidyNight:: getNightDeclareTime, subsidyNightDTO.getNightDeclareTime())
                .set(SubsidyNight:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyNight:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("夜班补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyNightDelDTO subsidyNightDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyNight> updateWrapper = new UpdateWrapper<SubsidyNight>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyNightDelDTO.getUids()), SubsidyNight:: getUid, subsidyNightDelDTO.getUids())
                .set(SubsidyNight:: getIsDeleted, System.currentTimeMillis())
                .set(SubsidyNight:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyNight:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("夜班补贴数据删除失败!");
        }
    }

}

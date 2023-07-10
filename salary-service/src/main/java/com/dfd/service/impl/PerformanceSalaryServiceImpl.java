package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.PerformanceSalaryDTO;
import com.dfd.dto.PerformanceSalaryDelDTO;
import com.dfd.dto.PerformanceSalaryInfoDTO;
import com.dfd.entity.*;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.PerformanceSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.PerformanceSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.PerformanceSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 17:06
 */
@Service
public class PerformanceSalaryServiceImpl extends ServiceImpl<PerformanceSalaryMapper, PerformanceSalary> implements PerformanceSalaryService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<PerformanceSalaryInfoVO> info(PerformanceSalaryInfoDTO performanceSalaryInfoDTO) {
        LambdaQueryWrapper<PerformanceSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(performanceSalaryInfoDTO.getItemUid()), PerformanceSalary:: getItemUid, performanceSalaryInfoDTO.getItemUid())
                .likeRight(performanceSalaryInfoDTO.getDeclareTime() !=null, PerformanceSalary:: getDeclareTime, performanceSalaryInfoDTO.getDeclareTime())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(PerformanceSalary :: getCreatedTime);

        Page<PerformanceSalary> pageReq = new Page(performanceSalaryInfoDTO.getCurrentPage(), performanceSalaryInfoDTO.getPageSize());
        IPage<PerformanceSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<PerformanceSalaryInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<PerformanceSalaryInfoVO> convertToSalaryInfoVO(List<PerformanceSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(PerformanceSalary::getItemUid).collect(Collectors.toList());
        Map<Integer, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(PerformanceSalary::getItemMemberUid).collect(Collectors.toList());
        Map<Integer, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<Integer, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<PerformanceSalaryInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("绩效数据为空");
            }
            PerformanceSalaryInfoVO salaryInfoVO = new PerformanceSalaryInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(PerformanceSalaryDTO performanceSalaryDTO) {
        LambdaQueryWrapper<PerformanceSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(performanceSalaryDTO.getItemUid()), PerformanceSalary:: getItemUid, performanceSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(performanceSalaryDTO.getItemMemberUid()), PerformanceSalary:: getItemMemberUid, performanceSalaryDTO.getItemMemberUid())
                .eq(performanceSalaryDTO.getDeclareTime()!=null, PerformanceSalary:: getDeclareTime, performanceSalaryDTO.getDeclareTime())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户绩效数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        PerformanceSalary salary = new PerformanceSalary();
        BeanUtil.copyProperties(performanceSalaryDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("绩效数据保存失败");
        }
    }

    @Override
    public void update(PerformanceSalaryDTO performanceSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<PerformanceSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(performanceSalaryDTO.getItemUid()), PerformanceSalary:: getItemUid, performanceSalaryDTO.getItemUid())
                .eq(StringUtils.isNotBlank(performanceSalaryDTO.getItemMemberUid()), PerformanceSalary:: getItemMemberUid, performanceSalaryDTO.getItemMemberUid())
                .eq(StringUtils.isNotBlank(performanceSalaryDTO.getDeclareTime()), PerformanceSalary:: getDeclareTime, performanceSalaryDTO.getDeclareTime())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set((performanceSalaryDTO.getPostSalaryStandard()!=null), PerformanceSalary:: getPostSalaryStandard, performanceSalaryDTO.getPostSalaryStandard())
                .set(StringUtils.isNotBlank(performanceSalaryDTO.getPerformanceRatio()), PerformanceSalary:: getPerformanceRatio, performanceSalaryDTO.getPerformanceRatio())
                .set((performanceSalaryDTO.getAttendanceMonths()!=null), PerformanceSalary:: getAttendanceMonths, performanceSalaryDTO.getAttendanceMonths())
                .set(performanceSalaryDTO.getPerformanceSalary()!=null, PerformanceSalary:: getPerformanceSalary, performanceSalaryDTO.getPerformanceSalary())
                .set((performanceSalaryDTO.getDeclareTime()!=null), PerformanceSalary:: getDeclareTime, performanceSalaryDTO.getDeclareTime())
                .set(StringUtils.isNotBlank(performanceSalaryDTO.getRemarks()), PerformanceSalary:: getRemarks, performanceSalaryDTO.getRemarks())
                .set(PerformanceSalary:: getUpdatedBy, currentUser.getPhone())
                .set(PerformanceSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态更新失败!");
        }
    }

    @Override
    public void delete(PerformanceSalaryDelDTO performanceSalaryDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<PerformanceSalary> updateWrapper = new UpdateWrapper<PerformanceSalary>()
                .lambda()
                .eq(StringUtils.isNotBlank(performanceSalaryDelDTO.getItemUid()), PerformanceSalary:: getItemUid, performanceSalaryDelDTO.getItemUid())
                .eq(StringUtils.isNotBlank(performanceSalaryDelDTO.getDeclareTime()), PerformanceSalary:: getDeclareTime, performanceSalaryDelDTO.getDeclareTime())
                .in(!CollectionUtils.isEmpty(performanceSalaryDelDTO.getItemMemberIds()), PerformanceSalary:: getItemMemberUid, performanceSalaryDelDTO.getItemMemberIds())
                .set(PerformanceSalary:: getIsDeleted, System.currentTimeMillis())
                .set(PerformanceSalary:: getUpdatedBy, currentUser.getPhone())
                .set(PerformanceSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }

}

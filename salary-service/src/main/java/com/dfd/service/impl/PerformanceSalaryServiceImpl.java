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
import com.dfd.mapper.PerformanceSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.PerformanceSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.PerformanceSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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

        List<PerformanceSalary> olist = baseMapper.selectList(queryWrapper);
        List<PerformanceSalaryInfoVO> list = convertToSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), performanceSalaryInfoDTO.getCurrentPage(),performanceSalaryInfoDTO.getPageSize(),list);
    }

    private List<PerformanceSalaryInfoVO> convertToSalaryInfoVO(List<PerformanceSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(PerformanceSalary::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(PerformanceSalary::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

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
    public void add(PerformanceSalaryAddDTO performanceSalaryAddDTO) {
        LambdaQueryWrapper<PerformanceSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(performanceSalaryAddDTO.getItemUid()), PerformanceSalary:: getItemUid, performanceSalaryAddDTO.getItemUid())
                .eq(StringUtils.isNotBlank(performanceSalaryAddDTO.getItemMemberUid()), PerformanceSalary:: getItemMemberUid, performanceSalaryAddDTO.getItemMemberUid())
                .likeRight(performanceSalaryAddDTO.getDeclareTime()!=null, PerformanceSalary:: getDeclareTime, performanceSalaryAddDTO.getDeclareTime())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户绩效数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        PerformanceSalary salary = new PerformanceSalary();
        BeanUtil.copyProperties(performanceSalaryAddDTO,salary);
        String uid = performanceSalaryAddDTO.getItemUid() + performanceSalaryAddDTO.getItemMemberUid() + DateUtil.getYM();
        salary.setUid(uid)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        if(salary.getPerformanceSalary() == null){
            BigDecimal multiply = salary.getPostSalaryStandard().multiply(new BigDecimal(salary.getPerformanceRatio()))
                    .multiply(new BigDecimal(salary.getAttendanceMonths()));
            salary.setPerformanceSalary(multiply);
        }
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("绩效数据保存失败");
        }
    }

    @Override
    public void update(PerformanceSalaryDTO performanceSalaryDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<PerformanceSalary> updateWrapper = new LambdaUpdateWrapper<>();
        String uid = performanceSalaryDTO.getItemUid() + performanceSalaryDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(performanceSalaryDTO.getUid()), PerformanceSalary:: getUid, performanceSalaryDTO.getUid())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(PerformanceSalary:: getUid, uid)
                .set((performanceSalaryDTO.getPostSalaryStandard()!=null), PerformanceSalary:: getPostSalaryStandard, performanceSalaryDTO.getPostSalaryStandard())
                .set(StringUtils.isNotBlank(performanceSalaryDTO.getPerformanceRatio()), PerformanceSalary:: getPerformanceRatio, performanceSalaryDTO.getPerformanceRatio())
                .set((performanceSalaryDTO.getAttendanceMonths()!=null), PerformanceSalary:: getAttendanceMonths, performanceSalaryDTO.getAttendanceMonths())
                .set(performanceSalaryDTO.getPerformanceSalary()!=null, PerformanceSalary:: getPerformanceSalary, performanceSalaryDTO.getPerformanceSalary())
                .set((performanceSalaryDTO.getDeclareTime()!=null), PerformanceSalary:: getDeclareTime, performanceSalaryDTO.getDeclareTime())
                .set(StringUtils.isNotBlank(performanceSalaryDTO.getRemarks()), PerformanceSalary:: getRemarks, performanceSalaryDTO.getRemarks())
                .set(PerformanceSalary:: getUpdatedBy, currentUser.getNumber())
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
                .likeRight(StringUtils.isNotBlank(performanceSalaryDelDTO.getDeclareTime()), PerformanceSalary:: getDeclareTime, performanceSalaryDelDTO.getDeclareTime())
                .in(!CollectionUtils.isEmpty(performanceSalaryDelDTO.getItemMemberUids()), PerformanceSalary:: getItemMemberUid, performanceSalaryDelDTO.getItemMemberUids())
                .set(PerformanceSalary:: getIsDeleted, System.currentTimeMillis())
                .set(PerformanceSalary:: getUpdatedBy, currentUser.getNumber())
                .set(PerformanceSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }

}

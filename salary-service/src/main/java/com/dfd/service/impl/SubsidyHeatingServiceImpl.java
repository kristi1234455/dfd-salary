package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyHeating;
import com.dfd.entity.User;
import com.dfd.mapper.SubsidyHeatingMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SubsidyHeatingService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyHeatingInfoVO;
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
public class SubsidyHeatingServiceImpl extends ServiceImpl<SubsidyHeatingMapper, SubsidyHeating> implements SubsidyHeatingService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<SubsidyHeatingInfoVO> info(SubsidyHeatingInfoDTO subsidyHeatingInfoDTO) {
        LambdaQueryWrapper<SubsidyHeating> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyHeatingInfoDTO.getItemUid()), SubsidyHeating:: getItemUid, subsidyHeatingInfoDTO.getItemUid())
                .likeRight(subsidyHeatingInfoDTO.getHeatingDeclareTime() !=null, SubsidyHeating:: getHeatingDeclareTime, subsidyHeatingInfoDTO.getHeatingDeclareTime())
                .eq(SubsidyHeating::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(SubsidyHeating :: getCreatedTime);

        List<SubsidyHeating> olist = baseMapper.selectList(queryWrapper);
//        olist = olist.stream().filter(e -> e.getHeatingDays() != null).collect(Collectors.toList());
        List<SubsidyHeatingInfoVO> list = convertToHeatingSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), subsidyHeatingInfoDTO.getCurrentPage(),subsidyHeatingInfoDTO.getPageSize(),list);
    }

    private List<SubsidyHeatingInfoVO> convertToHeatingSalaryInfoVO(List<SubsidyHeating> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(SubsidyHeating::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(SubsidyHeating::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<SubsidyHeatingInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("高温补贴数据为空");
            }
            SubsidyHeatingInfoVO salaryInfoVO = new SubsidyHeatingInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(SubsidyHeatingAddDTO subsidyHeatingDTO) {
        LambdaQueryWrapper<SubsidyHeating> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyHeatingDTO.getItemUid()), SubsidyHeating:: getItemUid, subsidyHeatingDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyHeatingDTO.getItemMemberUid()), SubsidyHeating:: getItemMemberUid, subsidyHeatingDTO.getItemMemberUid())
                .likeRight(subsidyHeatingDTO.getHeatingDeclareTime()!=null, SubsidyHeating:: getHeatingDeclareTime, subsidyHeatingDTO.getHeatingDeclareTime())
                .eq(subsidyHeatingDTO.getHeatingDays()!=null, SubsidyHeating:: getHeatingDays, subsidyHeatingDTO.getHeatingDays())
                .eq(SubsidyHeating::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户高温补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        SubsidyHeating salary = new SubsidyHeating();
        BeanUtil.copyProperties(subsidyHeatingDTO,salary);
        String uid = subsidyHeatingDTO.getItemUid() + subsidyHeatingDTO.getItemMemberUid() + DateUtil.getYM();
        salary.setUid(uid)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("高温补贴数据保存失败");
        }
    }

    @Override
    public void update(SubsidyHeatingDTO subsidyHeatingDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyHeating> updateWrapper = new LambdaUpdateWrapper<>();
        String uid = subsidyHeatingDTO.getItemUid() + subsidyHeatingDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyHeatingDTO.getUid()), SubsidyHeating:: getUid, subsidyHeatingDTO.getUid())
                .eq(SubsidyHeating::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(SubsidyHeating:: getUid, uid)
                .set((subsidyHeatingDTO.getIsHeatingGrant()!=null), SubsidyHeating:: getIsHeatingGrant, subsidyHeatingDTO.getIsHeatingGrant())
                .set((subsidyHeatingDTO.getHeatingSubsidyStandard()!=null), SubsidyHeating:: getHeatingSubsidyStandard, subsidyHeatingDTO.getHeatingSubsidyStandard())
                .set(subsidyHeatingDTO.getHeatingDays()!=null, SubsidyHeating:: getHeatingDays, subsidyHeatingDTO.getHeatingDays())
                .set(subsidyHeatingDTO.getHeatingSubsidy()!=null, SubsidyHeating:: getHeatingSubsidy, subsidyHeatingDTO.getHeatingSubsidy())
                .set((subsidyHeatingDTO.getHeatingRemarks()!=null), SubsidyHeating:: getHeatingRemarks, subsidyHeatingDTO.getHeatingRemarks())
                .set((subsidyHeatingDTO.getHeatingDeclareTime()!=null), SubsidyHeating:: getHeatingDeclareTime, subsidyHeatingDTO.getHeatingDeclareTime())
                .set(SubsidyHeating:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyHeating:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("高温补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyHeatingDelDTO subsidyHeatingDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyHeating> updateWrapper = new UpdateWrapper<SubsidyHeating>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyHeatingDelDTO.getUids()), SubsidyHeating:: getUid, subsidyHeatingDelDTO.getUids())
                .set(SubsidyHeating:: getIsDeleted, System.currentTimeMillis())
                .set(SubsidyHeating:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyHeating:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("高温补贴数据删除失败!");
        }
    }
}

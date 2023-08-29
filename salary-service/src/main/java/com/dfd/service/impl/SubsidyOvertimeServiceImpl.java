package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyOvertime;
import com.dfd.entity.User;
import com.dfd.mapper.SubsidyOvertimeMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SubsidyOvertimeService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyOvertimeInfoVO;
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
public class SubsidyOvertimeServiceImpl extends ServiceImpl<SubsidyOvertimeMapper, SubsidyOvertime> implements SubsidyOvertimeService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<SubsidyOvertimeInfoVO> info(SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO) {
        LambdaQueryWrapper<SubsidyOvertime> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeInfoDTO.getItemUid()), SubsidyOvertime:: getItemUid, subsidyOvertimeInfoDTO.getItemUid())
                .likeRight(subsidyOvertimeInfoDTO.getOvertimeDeclareTime() !=null, SubsidyOvertime:: getOvertimeDeclareTime, subsidyOvertimeInfoDTO.getOvertimeDeclareTime())
                .eq(SubsidyOvertime::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(SubsidyOvertime :: getCreatedTime);

        List<SubsidyOvertime> olist = baseMapper.selectList(queryWrapper);
//        olist = olist.stream().filter(e -> e.getOvertimeDays() != null).collect(Collectors.toList());
        List<SubsidyOvertimeInfoVO> list = convertToOverTimeSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), subsidyOvertimeInfoDTO.getCurrentPage(),subsidyOvertimeInfoDTO.getPageSize(),list);
    }

    private List<SubsidyOvertimeInfoVO> convertToOverTimeSalaryInfoVO(List<SubsidyOvertime> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(SubsidyOvertime::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(SubsidyOvertime::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<SubsidyOvertimeInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("加班补贴数据为空");
            }
            SubsidyOvertimeInfoVO salaryInfoVO = new SubsidyOvertimeInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(SubsidyOvertimeAddDTO subsidyOvertimeDTO) {
        LambdaQueryWrapper<SubsidyOvertime> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getItemUid()), SubsidyOvertime:: getItemUid, subsidyOvertimeDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getItemMemberUid()), SubsidyOvertime:: getItemMemberUid, subsidyOvertimeDTO.getItemMemberUid())
                .eq(subsidyOvertimeDTO.getOvertimeDays()!=null, SubsidyOvertime:: getOvertimeDays, subsidyOvertimeDTO.getOvertimeDays())
                .likeRight(subsidyOvertimeDTO.getOvertimeDeclareTime()!=null, SubsidyOvertime:: getOvertimeDeclareTime, subsidyOvertimeDTO.getOvertimeDeclareTime())
                .eq(SubsidyOvertime::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户加班补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        SubsidyOvertime salary = new SubsidyOvertime();
        BeanUtil.copyProperties(subsidyOvertimeDTO,salary);
        String uid = subsidyOvertimeDTO.getItemUid() + subsidyOvertimeDTO.getItemMemberUid() + DateUtil.getYM();
        salary.setUid(uid)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("加班补贴数据保存失败");
        }
    }

    @Override
    public void update(SubsidyOvertimeDTO subsidyOvertimeDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyOvertime> updateWrapper = new LambdaUpdateWrapper<>();
        String uid = subsidyOvertimeDTO.getItemUid() + subsidyOvertimeDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getUid()), SubsidyOvertime:: getUid, subsidyOvertimeDTO.getUid())
                .eq(SubsidyOvertime::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(SubsidyOvertime:: getUid, uid)
                .set(StringUtils.isNotBlank(subsidyOvertimeDTO.getOvertime()), SubsidyOvertime:: getOvertime, subsidyOvertimeDTO.getOvertime())
                .set(StringUtils.isNotBlank(subsidyOvertimeDTO.getOvertimeWorkContent()), SubsidyOvertime:: getOvertimeWorkContent, subsidyOvertimeDTO.getOvertimeWorkContent())
                .set((subsidyOvertimeDTO.getOvertimeSubsidyStandard()!=null), SubsidyOvertime:: getOvertimeSubsidyStandard, subsidyOvertimeDTO.getOvertimeSubsidyStandard())
                .set(subsidyOvertimeDTO.getOvertimeDays()!=null, SubsidyOvertime:: getOvertimeDays, subsidyOvertimeDTO.getOvertimeDays())
                .set(subsidyOvertimeDTO.getOvertimeSubsidy()!=null, SubsidyOvertime:: getOvertimeSubsidy, subsidyOvertimeDTO.getOvertimeSubsidy())
                .set((subsidyOvertimeDTO.getOvertimeRemarks()!=null), SubsidyOvertime:: getOvertimeRemarks, subsidyOvertimeDTO.getOvertimeRemarks())
                .set((subsidyOvertimeDTO.getOvertimeDeclareTime()!=null), SubsidyOvertime:: getOvertimeDeclareTime, subsidyOvertimeDTO.getOvertimeDeclareTime())
                .set(SubsidyOvertime:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyOvertime:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("加班补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyOvertimeDelDTO subsidyOvertimeDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyOvertime> updateWrapper = new UpdateWrapper<SubsidyOvertime>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyOvertimeDelDTO.getUids()), SubsidyOvertime:: getUid, subsidyOvertimeDelDTO.getUids())
                .set(SubsidyOvertime:: getIsDeleted, System.currentTimeMillis())
                .set(SubsidyOvertime:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyOvertime:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("加班补贴数据删除失败!");
        }
    }

}

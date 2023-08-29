package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyOut;
import com.dfd.entity.User;
import com.dfd.mapper.SubsidyOutMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SubsidyOutService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyOutInfoVO;
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
public class SubsidyOutServiceImpl extends ServiceImpl<SubsidyOutMapper, SubsidyOut> implements SubsidyOutService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;


    @Override
    public PageResult<SubsidyOutInfoVO> info(SubsidyOutInfoDTO subsidyOutInfoDTO) {
        LambdaQueryWrapper<SubsidyOut> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOutInfoDTO.getItemUid()), SubsidyOut:: getItemUid, subsidyOutInfoDTO.getItemUid())
                .likeRight(subsidyOutInfoDTO.getOutDeclareTime() !=null, SubsidyOut:: getOutDeclareTime, subsidyOutInfoDTO.getOutDeclareTime())
                .eq(SubsidyOut::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(SubsidyOut :: getCreatedTime);

        List<SubsidyOut> olist = baseMapper.selectList(queryWrapper);
//        olist = olist.stream().filter(e -> e.getOutDays() != null).collect(Collectors.toList());
        List<SubsidyOutInfoVO> list = convertToOutSalaryInfoVO(olist);
        return PageResult.infoPage(olist.size(), subsidyOutInfoDTO.getCurrentPage(),subsidyOutInfoDTO.getPageSize(),list);
    }

    private List<SubsidyOutInfoVO> convertToOutSalaryInfoVO(List<SubsidyOut> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(SubsidyOut::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(SubsidyOut::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<SubsidyOutInfoVO> result = list.stream().map(salary -> {
            if(!Optional.ofNullable(salary).isPresent()){
                throw new BusinessException("驻外补贴数据为空");
            }
            SubsidyOutInfoVO salaryInfoVO = new SubsidyOutInfoVO();
            BeanUtil.copyProperties(salary,salaryInfoVO);
            salaryInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(salary.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(salary.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(salary.getItemMemberUid()) : null);
            return salaryInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(SubsidyOutAddDTO subsidyOutDTO) {
        LambdaQueryWrapper<SubsidyOut> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOutDTO.getItemUid()), SubsidyOut:: getItemUid, subsidyOutDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyOutDTO.getItemMemberUid()), SubsidyOut:: getItemMemberUid, subsidyOutDTO.getItemMemberUid())
                .eq(subsidyOutDTO.getOutDays()!=null, SubsidyOut:: getOutDays, subsidyOutDTO.getOutDays())
                .eq(subsidyOutDTO.getOutDeclareTime()!=null, SubsidyOut:: getOutDeclareTime, subsidyOutDTO.getOutDeclareTime())
                .eq(SubsidyOut::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户驻外补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        SubsidyOut salary = new SubsidyOut();
        BeanUtil.copyProperties(subsidyOutDTO,salary);
        String uid = subsidyOutDTO.getItemUid() + subsidyOutDTO.getItemMemberUid() + DateUtil.getYM();
        salary.setUid(uid)
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(salary);
        if (!b) {
            throw new BusinessException("驻外补贴数据保存失败");
        }
    }

    @Override
    public void update(SubsidyOutDTO subsidyOutDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyOut> updateWrapper = new LambdaUpdateWrapper<>();
        String uid = subsidyOutDTO.getItemUid() + subsidyOutDTO.getItemMemberUid() + DateUtil.getYM();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyOutDTO.getUid()), SubsidyOut:: getUid, subsidyOutDTO.getUid())
                .eq(SubsidyOut::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(SubsidyOut:: getUid, uid)
                .set((subsidyOutDTO.getOutSubsidyStandard()!=null), SubsidyOut:: getOutSubsidyStandard, subsidyOutDTO.getOutSubsidyStandard())
                .set(subsidyOutDTO.getOutDays()!=null, SubsidyOut:: getOutDays, subsidyOutDTO.getOutDays())
                .set(subsidyOutDTO.getOutSubsidy()!=null, SubsidyOut:: getOutSubsidy, subsidyOutDTO.getOutSubsidy())
                .set((subsidyOutDTO.getOutRemarks()!=null), SubsidyOut:: getOutRemarks, subsidyOutDTO.getOutRemarks())
                .set((subsidyOutDTO.getOutDeclareTime()!=null), SubsidyOut:: getOutDeclareTime, subsidyOutDTO.getOutDeclareTime())
                .set(SubsidyOut:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyOut:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("驻外补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyOutDelDTO subsidyOutDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SubsidyOut> updateWrapper = new UpdateWrapper<SubsidyOut>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyOutDelDTO.getUids()), SubsidyOut:: getUid, subsidyOutDelDTO.getUids())
                .set(SubsidyOut:: getIsDeleted, System.currentTimeMillis())
                .set(SubsidyOut:: getUpdatedBy, currentUser.getNumber())
                .set(SubsidyOut:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("驻外补贴数据删除失败!");
        }
    }

}

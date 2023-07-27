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
import com.dfd.entity.BidSalary;
import com.dfd.entity.DesignSalary;
import com.dfd.entity.TotalSalary;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.TotalSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.BidSalaryInfoVO;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class TotalSalaryServiceImpl extends ServiceImpl<TotalSalaryMapper, TotalSalary> implements TotalSalaryService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO) {
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(specialInfoDTO.getItemUid()), TotalSalary:: getItemUid, specialInfoDTO.getItemUid())
                .likeRight(specialInfoDTO.getSpecialDeclareTime() !=null, TotalSalary:: getSpecialDeclareTime, specialInfoDTO.getSpecialDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);

        Page<TotalSalary> pageReq = new Page(specialInfoDTO.getCurrentPage(), specialInfoDTO.getPageSize());
        IPage<TotalSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<SpecialInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<SpecialInfoVO> convertToSalaryInfoVO(List<TotalSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(TotalSalary::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(TotalSalary::getItemMemberUid).collect(Collectors.toList());
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);
        Map<String, String> itemMemberNumbers = memberService.queryNumberByUids(memUIdList);

        List<SpecialInfoVO> result = list.stream().map(element -> {
            if(!Optional.ofNullable(element).isPresent()){
                throw new BusinessException("投标数据为空");
            }
            SpecialInfoVO specialInfoVO = new SpecialInfoVO();
            BeanUtil.copyProperties(element,specialInfoVO);
            specialInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(element.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(element.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(element.getItemMemberUid()) : null);
            return specialInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void addSpecial(SpecialAddDTO speciaAddlDTO) {
//        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
//        queryWrapper.eq(StringUtils.isNotBlank(speciaAddlDTO.getItemUid()), TotalSalary:: getItemUid, speciaAddlDTO.getItemUid())
//                .eq(speciaAddlDTO.getDeclareTime()!=null, TotalSalary:: getDeclareTime, speciaAddlDTO.getDeclareTime())
//                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        if(baseMapper.exists(queryWrapper)){
//            throw new BusinessException("添加失败，专岗工资数据已经存在！");
//        }
        User currentUser = UserRequest.getCurrentUser();
        TotalSalary totalSalary = new TotalSalary();
        BeanUtil.copyProperties(speciaAddlDTO,totalSalary);
        totalSalary.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(totalSalary);
        if (!b) {
            throw new BusinessException("专岗工资保存失败");
        }
    }

    @Override
    public void updateSpecial(SpecialDTO specialVO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<TotalSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(specialVO.getUid()), TotalSalary:: getUid, specialVO.getUid())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(specialVO.getDepartment()), TotalSalary:: getDepartment, specialVO.getDepartment())
                .set(StringUtils.isNotBlank(specialVO.getSpecialBaseFee()), TotalSalary:: getSpecialBaseFee, specialVO.getSpecialBaseFee())
                .set(StringUtils.isNotBlank(specialVO.getSpecialMaxCoefficient()), TotalSalary:: getSpecialMaxCoefficient, specialVO.getSpecialMaxCoefficient())
                .set(StringUtils.isNotBlank(specialVO.getStandardSubsidy()), TotalSalary:: getStandardSubsidy, specialVO.getStandardSubsidy())
                .set(StringUtils.isNotBlank(specialVO.getSubsidyCoefficient()), TotalSalary:: getSubsidyCoefficient, specialVO.getSubsidyCoefficient())
                .set(StringUtils.isNotBlank(specialVO.getRemarks()), TotalSalary:: getRemarks, specialVO.getRemarks())
                .set((specialVO.getSpecialDeclareTime()!=null), TotalSalary:: getSpecialDeclareTime, specialVO.getSpecialDeclareTime())
                .set(TotalSalary:: getUpdatedBy, currentUser.getNumber())
                .set(TotalSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("专岗工资更新失败!");
        }
    }

    @Override
    public void delSpecial(SpecialDelDTO specialDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<TotalSalary> updateWrapper = new UpdateWrapper<TotalSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(specialDelDTO.getUids()), TotalSalary:: getUid, specialDelDTO.getUids())
                .set(TotalSalary:: getIsDeleted, System.currentTimeMillis())
                .set(TotalSalary:: getUpdatedBy, currentUser.getNumber())
                .set(TotalSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }

    @Override
    public TotalSalaryInfoVO info(TotalSalaryInfoDTO totalSalaryInfoDTO) {

        return null;
    }

    private void flushTotaSalary(){

    }
}













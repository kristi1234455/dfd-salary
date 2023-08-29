package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.SpecialAddDTO;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.SpecialSalary;
import com.dfd.entity.SpecialSalary;
import com.dfd.entity.User;
import com.dfd.mapper.SpecialSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SpecialSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/8/29 10:18
 */
@Service
public class SpecialSalaryServiceImpl extends ServiceImpl<SpecialSalaryMapper, SpecialSalary> implements SpecialSalaryService {


    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO) {
        LambdaQueryWrapper<SpecialSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(specialInfoDTO.getItemUid()), SpecialSalary:: getItemUid, specialInfoDTO.getItemUid())
                .likeRight(specialInfoDTO.getSpecialDeclareTime() !=null, SpecialSalary:: getSpecialDeclareTime, specialInfoDTO.getSpecialDeclareTime())
                .eq(SpecialSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(SpecialSalary :: getCreatedTime);

        Page<SpecialSalary> pageReq = new Page(specialInfoDTO.getCurrentPage(), specialInfoDTO.getPageSize());
        IPage<SpecialSalary> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<SpecialInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToSalaryInfoVO(page.getRecords()));
        return pageResult;
    }

    private List<SpecialInfoVO> convertToSalaryInfoVO(List<SpecialSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(SpecialSalary::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(SpecialSalary::getItemMemberUid).collect(Collectors.toList());
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
        LambdaQueryWrapper<SpecialSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(speciaAddlDTO.getItemUid()), SpecialSalary:: getItemUid, speciaAddlDTO.getItemUid())
                .eq(StringUtils.isNotBlank(speciaAddlDTO.getItemMemberUid()), SpecialSalary:: getItemMemberUid, speciaAddlDTO.getItemMemberUid())
                .likeRight(speciaAddlDTO.getSpecialDeclareTime()!=null, SpecialSalary:: getSpecialDeclareTime, speciaAddlDTO.getSpecialDeclareTime())
                .eq(SpecialSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，专岗工资数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        String uid = speciaAddlDTO.getItemUid() + speciaAddlDTO.getItemMemberUid() + DateUtil.getYM();
        SpecialSalary SpecialSalary = new SpecialSalary();
        BeanUtil.copyProperties(speciaAddlDTO,SpecialSalary);
        SpecialSalary.setUid(uid)
                .setSpecialDeclareTime(speciaAddlDTO.getSpecialDeclareTime())
                .setCreatedBy(currentUser.getNumber())
                .setUpdatedBy(currentUser.getNumber())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(SpecialSalary);
        if (!b) {
            throw new BusinessException("专岗工资保存失败");
        }
    }

    @Override
    public void updateSpecial(SpecialDTO specialVO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SpecialSalary> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(specialVO.getUid()), SpecialSalary:: getUid, specialVO.getUid())
                .eq(SpecialSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(specialVO.getDepartment()), SpecialSalary:: getDepartment, specialVO.getDepartment())
                .set(StringUtils.isNotBlank(specialVO.getSpecialBaseFee()), SpecialSalary:: getSpecialBaseFee, specialVO.getSpecialBaseFee())
                .set(StringUtils.isNotBlank(specialVO.getSpecialMaxCoefficient()), SpecialSalary:: getSpecialMaxCoefficient, specialVO.getSpecialMaxCoefficient())
                .set(StringUtils.isNotBlank(specialVO.getStandardSubsidy()), SpecialSalary:: getStandardSubsidy, specialVO.getStandardSubsidy())
                .set(StringUtils.isNotBlank(specialVO.getSubsidyCoefficient()), SpecialSalary:: getSubsidyCoefficient, specialVO.getSubsidyCoefficient())
                .set(StringUtils.isNotBlank(specialVO.getRemarks()), SpecialSalary:: getRemarks, specialVO.getRemarks())
                .set(StringUtils.isNotBlank(specialVO.getSpecialDeclareTime()), SpecialSalary:: getSpecialDeclareTime,specialVO.getSpecialDeclareTime())
                .set((specialVO.getSpecialDeclareTime()!=null), SpecialSalary:: getSpecialDeclareTime, specialVO.getSpecialDeclareTime())
                .set(SpecialSalary:: getUpdatedBy, currentUser.getNumber())
                .set(SpecialSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("专岗工资更新失败!");
        }
    }

    @Override
    public void delSpecial(SpecialDelDTO specialDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<SpecialSalary> updateWrapper = new UpdateWrapper<SpecialSalary>()
                .lambda()
                .in(!CollectionUtils.isEmpty(specialDelDTO.getUids()), SpecialSalary:: getUid, specialDelDTO.getUids())
                .set(SpecialSalary:: getIsDeleted, System.currentTimeMillis())
                .set(SpecialSalary:: getUpdatedBy, currentUser.getNumber())
                .set(SpecialSalary:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("设计状态删除失败!");
        }
    }

}

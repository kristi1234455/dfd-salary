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
import com.dfd.enums.ItemStageEnum;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.*;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.PageResult;
import com.dfd.vo.*;
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

    @Autowired
    private TotalSalaryItemService totalSalaryItemService;

    @Autowired
    private FlushTotalSalaryService flushTotalSalaryService;

    @Override
    public PageResult<TotalSalaryInfoVO> info(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        flushTotalSalaryService.flushMonthTotalSalary();
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalary:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalary:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> olist = baseMapper.selectList(queryWrapper);
        List<TotalSalaryInfoVO> list = convertToTotalSalaryInfoVO(totalSalaryInfoDTO,olist);
        return PageResult.infoPage(olist.size(), totalSalaryInfoDTO.getCurrentPage(),totalSalaryInfoDTO.getPageSize(),list);
    }

    @Override
    public List<TotalSalarySumItemInfoVO> infoItem(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.ne(Item::getItemStage, ItemStageEnum.STAGE_FINISH.getCode())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Item :: getCreatedTime);
        List<Item> olist = itemService.list(queryWrapper);
        return convertToItemInfoVO(olist);
    }

    private List<TotalSalarySumItemInfoVO> convertToItemInfoVO(List<Item> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TotalSalarySumItemInfoVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("项目总数据为空");
            }
            TotalSalarySumItemInfoVO resultVO = new TotalSalarySumItemInfoVO();
            BeanUtil.copyProperties(var,resultVO);
            return resultVO;
        }).collect(Collectors.toList());
        return result;
    }

    private List<TotalSalaryInfoVO> convertToTotalSalaryInfoVO(TotalSalaryInfoDTO totalSalaryInfoDTO, List<TotalSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<TotalSalaryItem> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalaryItem:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalaryItem:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalaryItem:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalaryItem::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalaryItem :: getCreatedTime);
        List<TotalSalaryItem> totalSalaryItems = totalSalaryItemService.list(queryWrapper);

        List<String> memUIdList = new ArrayList<>();
        for (TotalSalaryItem totalSalaryItem : totalSalaryItems) {
            memUIdList.add(totalSalaryItem.getItemManager());
            memUIdList.add(totalSalaryItem.getBidDirector());
            memUIdList.add(totalSalaryItem.getDesignManager());
            memUIdList.add(totalSalaryItem.getScientificManager());
        }
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);

        List<TotalSalaryItemInfoVO> totalSalaryItemInfoVOS = totalSalaryItems.stream().map(var -> {
            TotalSalaryItemInfoVO infoVO = new TotalSalaryItemInfoVO();
            BeanUtil.copyProperties(var, infoVO);
            infoVO.setItemManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getItemManager()) : null)
                    .setBidDirector(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getBidDirector()) : null)
                    .setDesignManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getDesignManager()) : null)
                    .setScientificManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getScientificManager()) : null);
            return infoVO;
        }).collect(Collectors.toList());
        Map<String, List<TotalSalaryItemInfoVO>> itemMemberUidTotalSalaryItem = totalSalaryItemInfoVOS.stream()
                .collect(Collectors.groupingBy(TotalSalaryItemInfoVO::getItemMemberUid));

        List<TotalSalaryInfoVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("项目工资部门汇总数据为空");
            }
            TotalSalaryInfoVO resultVO = new TotalSalaryInfoVO();
            TotalSalaryBasicInfoVO basicInfoVO = new TotalSalaryBasicInfoVO();
            BeanUtil.copyProperties(var,basicInfoVO);
            resultVO.setTotalSalaryBasicInfoVO(basicInfoVO);
            resultVO.setTotalSalaryItemInfoVOList(itemMemberUidTotalSalaryItem.get(var.getItemMemberUid()));
            return resultVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public int exportSummarySalaryCount(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalary:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalary:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        return Integer.parseInt(String.valueOf(baseMapper.selectCount(queryWrapper)));
    }

    @Override
    public List<TotalSalarySummaryExportVO> exportSummarySalaryList(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalary:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalary:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> list = baseMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        LambdaQueryWrapper<TotalSalaryItem> wrapper = new LambdaQueryWrapper();
        wrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalaryItem:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalaryItem:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalaryItem:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalaryItem::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        wrapper.orderByDesc(TotalSalaryItem :: getCreatedTime);
        List<TotalSalaryItem> totalSalaryItems = totalSalaryItemService.list(wrapper);

        List<String> memUIdList = new ArrayList<>();
        for (TotalSalaryItem totalSalaryItem : totalSalaryItems) {
            memUIdList.add(totalSalaryItem.getItemManager());
            memUIdList.add(totalSalaryItem.getBidDirector());
            memUIdList.add(totalSalaryItem.getDesignManager());
            memUIdList.add(totalSalaryItem.getScientificManager());
        }
        Map<String, String> itemMemberNames = memberService.queryNameByUids(memUIdList);

        List<TotalSalaryItemInfoVO> totalSalaryItemInfoVOS = totalSalaryItems.stream().map(var -> {
            TotalSalaryItemInfoVO infoVO = new TotalSalaryItemInfoVO();
            BeanUtil.copyProperties(var, infoVO);
            infoVO.setItemManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getItemManager()) : null)
                    .setBidDirector(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getBidDirector()) : null)
                    .setDesignManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getDesignManager()) : null)
                    .setScientificManager(!itemMemberNames.isEmpty() ? itemMemberNames.get(var.getScientificManager()) : null);
            return infoVO;
        }).collect(Collectors.toList());
        Map<String, List<TotalSalaryItemInfoVO>> itemMemberUidTotalSalaryItem = totalSalaryItemInfoVOS.stream().collect(Collectors.groupingBy(TotalSalaryItemInfoVO::getItemMemberUid));

        List<TotalSalarySummaryExportVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("工资综合汇总数据为空！");
            }
            TotalSalarySummaryExportVO resultVO = new TotalSalarySummaryExportVO();
            TotalSalaryBasicInfoVO basicInfoVO = new TotalSalaryBasicInfoVO();
            BeanUtil.copyProperties(var,basicInfoVO);
            resultVO.setTotalSalaryItemInfoVOList(itemMemberUidTotalSalaryItem.get(var.getItemMemberUid()));
            return resultVO;
        }).collect(Collectors.toList());
        return result;
    }


    @Override
    public PageResult<TotalSalaryPayrollInfoVO> infoPayroll(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO) {
//        totalSalaryFlushService.flushMonthTotalSalary();
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getRoom()), TotalSalary:: getRoom, totalSalaryPayrollInfoDTO.getRoom())
                 .like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getName()), TotalSalary:: getName, totalSalaryPayrollInfoDTO.getName())
                .likeRight(totalSalaryPayrollInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryPayrollInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> olist = baseMapper.selectList(queryWrapper);
        List<TotalSalaryPayrollInfoVO> list = convertToRoomInfoVO(olist);
        return PageResult.infoPage(olist.size(), totalSalaryPayrollInfoDTO.getCurrentPage(),totalSalaryPayrollInfoDTO.getPageSize(),list);
    }

    private List<TotalSalaryPayrollInfoVO> convertToRoomInfoVO(List<TotalSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TotalSalaryPayrollInfoVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("项目工资部门汇总数据为空");
            }
            TotalSalaryPayrollInfoVO infoVO = new TotalSalaryPayrollInfoVO();
            BeanUtil.copyProperties(var,infoVO);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public int exportSalaryCount(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO) {
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getRoom()), TotalSalary:: getRoom, totalSalaryPayrollInfoDTO.getRoom())
                .like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getName()), TotalSalary:: getName, totalSalaryPayrollInfoDTO.getName())
                .likeRight(totalSalaryPayrollInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryPayrollInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        return Integer.parseInt(String.valueOf(baseMapper.selectCount(queryWrapper)));
    }

    @Override
    public List<TotalSalaryPayrollExportVO> exportSalaryList(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO) {
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getRoom()), TotalSalary:: getRoom, totalSalaryPayrollInfoDTO.getRoom())
                .like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getName()), TotalSalary:: getName, totalSalaryPayrollInfoDTO.getName())
                .likeRight(totalSalaryPayrollInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryPayrollInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> list = list(queryWrapper);

        List<TotalSalaryPayrollExportVO> result = list.stream().map(var -> {
            TotalSalaryPayrollExportVO infoVO = new TotalSalaryPayrollExportVO();
            BeanUtil.copyProperties(var, infoVO);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }


}













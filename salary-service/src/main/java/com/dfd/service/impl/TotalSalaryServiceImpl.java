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
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.TotalSalaryService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
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
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(speciaAddlDTO.getItemUid()), TotalSalary:: getItemUid, speciaAddlDTO.getItemUid())
                .likeRight(speciaAddlDTO.getSpecialDeclareTime()!=null, TotalSalary:: getSpecialDeclareTime, speciaAddlDTO.getSpecialDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，专岗工资数据已经存在！");
        }
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
    public PageResult<TotalSalaryInfoVO> info(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        flushTotalSalary();
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryInfoDTO.getNumber()), TotalSalary:: getNumber, totalSalaryInfoDTO.getNumber())
                .like(StringUtils.isNotBlank(totalSalaryInfoDTO.getName()), TotalSalary:: getName, totalSalaryInfoDTO.getName())
                .likeRight(totalSalaryInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> olist = baseMapper.selectList(queryWrapper);

        Integer pageNum = totalSalaryInfoDTO.getCurrentPage();
        Integer pageSize = totalSalaryInfoDTO.getPageSize();
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (olist.size() + pageSize - 1) / pageSize;
        List<TotalSalaryInfoVO> list = convertToTotalSalaryInfoVO(olist);
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
        PageResult<TotalSalaryInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<TotalSalaryInfoVO> convertToTotalSalaryInfoVO(List<TotalSalary> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<TotalSalaryInfoVO> result = list.stream().map(var -> {
            if(!Optional.ofNullable(var).isPresent()){
                throw new BusinessException("项目工资部门汇总数据为空");
            }
            TotalSalaryInfoVO infoVO = new TotalSalaryInfoVO();
            BeanUtil.copyProperties(var,infoVO);
            return infoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public int exportSummarySalaryCount(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        return 0;
    }

    @Override
    public List<TotalSalarySummaryExportVO> exportSummarySalaryList(TotalSalaryInfoDTO totalSalaryInfoDTO) {
        return null;
    }


    public void flushTotalSalary() {
        //todo


    }


    @Override
    public PageResult<TotalSalaryPayrollInfoVO> infoPayroll(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO) {
        flushTotalSalary();
        LambdaQueryWrapper<TotalSalary> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getRoom()), TotalSalary:: getRoom, totalSalaryPayrollInfoDTO.getRoom())
                 .like(StringUtils.isNotBlank(totalSalaryPayrollInfoDTO.getName()), TotalSalary:: getName, totalSalaryPayrollInfoDTO.getName())
                .likeRight(totalSalaryPayrollInfoDTO.getDeclareTime() !=null, TotalSalary:: getDeclareTime, totalSalaryPayrollInfoDTO.getDeclareTime())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(TotalSalary :: getCreatedTime);
        List<TotalSalary> olist = baseMapper.selectList(queryWrapper);

        Integer pageNum = totalSalaryPayrollInfoDTO.getCurrentPage();
        Integer pageSize = totalSalaryPayrollInfoDTO.getPageSize();
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (olist.size() + pageSize - 1) / pageSize;
        List<TotalSalaryPayrollInfoVO> list = convertToRoomInfoVO(olist);
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
        PageResult<TotalSalaryPayrollInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
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













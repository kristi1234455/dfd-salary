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
import com.dfd.dto.MemberInfoVO;
import com.dfd.entity.*;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.SubsidyMapper;
import com.dfd.service.ItemService;
import com.dfd.service.MemberService;
import com.dfd.service.SubsidyService;
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
 * @date 2023/6/13 16:54
 */
@Service
public class SubsidyServiceImpl extends ServiceImpl<SubsidyMapper, Subsidy> implements SubsidyService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MemberService memberService;

    @Override
    public PageResult<SubsidyOvertimeInfoVO> info(SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO) {
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeInfoDTO.getItemUid()), Subsidy:: getItemUid, subsidyOvertimeInfoDTO.getItemUid())
                .eq(subsidyOvertimeInfoDTO.getOvertimeDeclareTime() !=null, Subsidy:: getOvertimeDeclareTime, subsidyOvertimeInfoDTO.getOvertimeDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Subsidy :: getCreatedTime);

        Integer pageNum = subsidyOvertimeInfoDTO.getCurrentPage();
        Integer pageSize = subsidyOvertimeInfoDTO.getPageSize();
        List<Subsidy> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<SubsidyOvertimeInfoVO> list = convertToOverTimeSalaryInfoVO(members);
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
        PageResult<SubsidyOvertimeInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<SubsidyOvertimeInfoVO> convertToOverTimeSalaryInfoVO(List<Subsidy> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(Subsidy::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Subsidy::getItemMemberUid).collect(Collectors.toList());
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
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getItemUid()), Subsidy:: getItemUid, subsidyOvertimeDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getItemMemberUid()), Subsidy:: getItemMemberUid, subsidyOvertimeDTO.getItemMemberUid())
                .eq(subsidyOvertimeDTO.getOvertimeDeclareTime()!=null, Subsidy:: getOvertimeDeclareTime, subsidyOvertimeDTO.getOvertimeDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户加班补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Subsidy salary = new Subsidy();
        BeanUtil.copyProperties(subsidyOvertimeDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
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
        LambdaUpdateWrapper<Subsidy> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyOvertimeDTO.getUid()), Subsidy:: getUid, subsidyOvertimeDTO.getUid())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(subsidyOvertimeDTO.getOvertime()), Subsidy:: getOvertime, subsidyOvertimeDTO.getOvertime())
                .set(StringUtils.isNotBlank(subsidyOvertimeDTO.getOvertimeWorkContent()), Subsidy:: getOvertimeWorkContent, subsidyOvertimeDTO.getOvertimeWorkContent())
                .set((subsidyOvertimeDTO.getOvertimeSubsidyStandard()!=null), Subsidy:: getOvertimeSubsidyStandard, subsidyOvertimeDTO.getOvertimeSubsidyStandard())
                .set(subsidyOvertimeDTO.getOvertimeDays()!=null, Subsidy:: getOvertimeDays, subsidyOvertimeDTO.getOvertimeDays())
                .set(subsidyOvertimeDTO.getOvertimeSubsidy()!=null, Subsidy:: getOvertimeSubsidy, subsidyOvertimeDTO.getOvertimeSubsidy())
                .set((subsidyOvertimeDTO.getOvertimeRemarks()!=null), Subsidy:: getOvertimeRemarks, subsidyOvertimeDTO.getOvertimeRemarks())
                .set((subsidyOvertimeDTO.getOvertimeDeclareTime()!=null), Subsidy:: getOvertimeDeclareTime, subsidyOvertimeDTO.getOvertimeDeclareTime())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("加班补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyOvertimeDelDTO subsidyOvertimeDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Subsidy> updateWrapper = new UpdateWrapper<Subsidy>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyOvertimeDelDTO.getUids()), Subsidy:: getUid, subsidyOvertimeDelDTO.getUids())
                .set(Subsidy:: getIsDeleted, System.currentTimeMillis())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("加班补贴数据删除失败!");
        }
    }

    @Override
    public PageResult<SubsidyNightInfoVO> info(SubsidyNightInfoDTO subsidyNightInfoDTO) {
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyNightInfoDTO.getItemUid()), Subsidy:: getItemUid, subsidyNightInfoDTO.getItemUid())
                .eq(subsidyNightInfoDTO.getNightDeclareTime() !=null, Subsidy:: getOvertimeDeclareTime, subsidyNightInfoDTO.getNightDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Subsidy :: getCreatedTime);


        Integer pageNum = subsidyNightInfoDTO.getCurrentPage();
        Integer pageSize = subsidyNightInfoDTO.getPageSize();
        List<Subsidy> members = baseMapper.selectList(queryWrapper);
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

    private List<SubsidyNightInfoVO> convertToNightSalaryInfoVO(List<Subsidy> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(Subsidy::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Subsidy::getItemMemberUid).collect(Collectors.toList());
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
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyNightDTO.getItemUid()), Subsidy:: getItemUid, subsidyNightDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyNightDTO.getItemMemberUid()), Subsidy:: getItemMemberUid, subsidyNightDTO.getItemMemberUid())
                .eq(subsidyNightDTO.getNightDeclareTime()!=null, Subsidy:: getOvertimeDeclareTime, subsidyNightDTO.getNightDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户夜班补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Subsidy salary = new Subsidy();
        BeanUtil.copyProperties(subsidyNightDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
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
        LambdaUpdateWrapper<Subsidy> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyNightDTO.getUid()), Subsidy:: getUid, subsidyNightDTO.getUid())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set(StringUtils.isNotBlank(subsidyNightDTO.getNightDuty()), Subsidy:: getNightDuty, subsidyNightDTO.getNightDuty())
                .set(StringUtils.isNotBlank(subsidyNightDTO.getNightWorkContent()), Subsidy:: getNightWorkContent, subsidyNightDTO.getNightWorkContent())
                .set((subsidyNightDTO.getNightSubsidyStandard()!=null), Subsidy:: getNightSubsidyStandard, subsidyNightDTO.getNightSubsidyStandard())
                .set(subsidyNightDTO.getNightDays()!=null, Subsidy:: getNightDays, subsidyNightDTO.getNightDays())
                .set(subsidyNightDTO.getNightSubsidy()!=null, Subsidy:: getNightSubsidy, subsidyNightDTO.getNightSubsidy())
                .set((subsidyNightDTO.getNightRemarks()!=null), Subsidy:: getNightRemarks, subsidyNightDTO.getNightRemarks())
                .set((subsidyNightDTO.getNightDeclareTime()!=null), Subsidy:: getNightDeclareTime, subsidyNightDTO.getNightDeclareTime())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("夜班补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyNightDelDTO subsidyNightDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Subsidy> updateWrapper = new UpdateWrapper<Subsidy>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyNightDelDTO.getUids()), Subsidy:: getUid, subsidyNightDelDTO.getUids())
                .set(Subsidy:: getIsDeleted, System.currentTimeMillis())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("夜班补贴数据删除失败!");
        }
    }

    @Override
    public PageResult<SubsidyOutInfoVO> info(SubsidyOutInfoDTO subsidyOutInfoDTO) {
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOutInfoDTO.getItemUid()), Subsidy:: getItemUid, subsidyOutInfoDTO.getItemUid())
                .eq(subsidyOutInfoDTO.getOutDeclareTime() !=null, Subsidy:: getOvertimeDeclareTime, subsidyOutInfoDTO.getOutDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Subsidy :: getCreatedTime);

        Integer pageNum = subsidyOutInfoDTO.getCurrentPage();
        Integer pageSize = subsidyOutInfoDTO.getPageSize();
        List<Subsidy> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<SubsidyOutInfoVO> list = convertToOutSalaryInfoVO(members);
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
        PageResult<SubsidyOutInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<SubsidyOutInfoVO> convertToOutSalaryInfoVO(List<Subsidy> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(Subsidy::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Subsidy::getItemMemberUid).collect(Collectors.toList());
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
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyOutDTO.getItemUid()), Subsidy:: getItemUid, subsidyOutDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyOutDTO.getItemMemberUid()), Subsidy:: getItemMemberUid, subsidyOutDTO.getItemMemberUid())
                .eq(subsidyOutDTO.getOutDeclareTime()!=null, Subsidy:: getOvertimeDeclareTime, subsidyOutDTO.getOutDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户驻外补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Subsidy salary = new Subsidy();
        BeanUtil.copyProperties(subsidyOutDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
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
        LambdaUpdateWrapper<Subsidy> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyOutDTO.getUid()), Subsidy:: getUid, subsidyOutDTO.getUid())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set((subsidyOutDTO.getOutSubsidyStandard()!=null), Subsidy:: getOutSubsidyStandard, subsidyOutDTO.getOutSubsidyStandard())
                .set(subsidyOutDTO.getOutDays()!=null, Subsidy:: getOutDays, subsidyOutDTO.getOutDays())
                .set(subsidyOutDTO.getOutSubsidy()!=null, Subsidy:: getOutSubsidy, subsidyOutDTO.getOutSubsidy())
                .set((subsidyOutDTO.getOutRemarks()!=null), Subsidy:: getOutRemarks, subsidyOutDTO.getOutRemarks())
                .set((subsidyOutDTO.getOutDeclareTime()!=null), Subsidy:: getOutDeclareTime, subsidyOutDTO.getOutDeclareTime())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("驻外补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyOutDelDTO subsidyOutDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Subsidy> updateWrapper = new UpdateWrapper<Subsidy>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyOutDelDTO.getUids()), Subsidy:: getUid, subsidyOutDelDTO.getUids())
                .set(Subsidy:: getIsDeleted, System.currentTimeMillis())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("驻外补贴数据删除失败!");
        }
    }

    @Override
    public PageResult<SubsidyHeatingInfoVO> info(SubsidyHeatingInfoDTO subsidyHeatingInfoDTO) {
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyHeatingInfoDTO.getItemUid()), Subsidy:: getItemUid, subsidyHeatingInfoDTO.getItemUid())
                .eq(subsidyHeatingInfoDTO.getHeatingDeclareTime() !=null, Subsidy:: getOvertimeDeclareTime, subsidyHeatingInfoDTO.getHeatingDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        queryWrapper.orderByDesc(Subsidy :: getCreatedTime);

        Integer pageNum = subsidyHeatingInfoDTO.getCurrentPage();
        Integer pageSize = subsidyHeatingInfoDTO.getPageSize();
        List<Subsidy> members = baseMapper.selectList(queryWrapper);
        //总页数
//        int totalPage = list.size() / pageSize;
        int totalPage = (members.size() + pageSize - 1) / pageSize;
        List<SubsidyHeatingInfoVO> list = convertToHeatingSalaryInfoVO(members);
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
        PageResult<SubsidyHeatingInfoVO> pageResult = new PageResult<>();
        pageResult.setCurrentPage(pageNum)
                .setPageSize(pageSize)
                .setRecords(list)
                .setTotalPages(totalPage)
                .setTotalRecords(size);
        return pageResult;
    }

    private List<SubsidyHeatingInfoVO> convertToHeatingSalaryInfoVO(List<Subsidy> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        List<String> itemUIdList = list.stream().map(Subsidy::getItemUid).collect(Collectors.toList());
        Map<String, String> itemNames = itemService.queryNameByUids(itemUIdList);

        List<String> memUIdList = list.stream().map(Subsidy::getItemMemberUid).collect(Collectors.toList());
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
        LambdaQueryWrapper<Subsidy> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(subsidyHeatingDTO.getItemUid()), Subsidy:: getItemUid, subsidyHeatingDTO.getItemUid())
                .eq(StringUtils.isNotBlank(subsidyHeatingDTO.getItemMemberUid()), Subsidy:: getItemMemberUid, subsidyHeatingDTO.getItemMemberUid())
                .eq(subsidyHeatingDTO.getHeatingDeclareTime()!=null, Subsidy:: getOvertimeDeclareTime, subsidyHeatingDTO.getHeatingDeclareTime())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户高温补贴数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Subsidy salary = new Subsidy();
        BeanUtil.copyProperties(subsidyHeatingDTO,salary);
        salary.setUid(UUIDUtil.getUUID32Bits())
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
        LambdaUpdateWrapper<Subsidy> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(subsidyHeatingDTO.getUid()), Subsidy:: getUid, subsidyHeatingDTO.getUid())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO)
                .set((subsidyHeatingDTO.getHeatingSubsidyStandard()!=null), Subsidy:: getHeatingSubsidyStandard, subsidyHeatingDTO.getHeatingSubsidyStandard())
                .set(subsidyHeatingDTO.getHeatingDays()!=null, Subsidy:: getHeatingDays, subsidyHeatingDTO.getHeatingDays())
                .set(subsidyHeatingDTO.getHeatingSubsidy()!=null, Subsidy:: getHeatingSubsidy, subsidyHeatingDTO.getHeatingSubsidy())
                .set((subsidyHeatingDTO.getHeatingRemarks()!=null), Subsidy:: getHeatingRemarks, subsidyHeatingDTO.getHeatingRemarks())
                .set((subsidyHeatingDTO.getHeatingDeclareTime()!=null), Subsidy:: getHeatingDeclareTime, subsidyHeatingDTO.getHeatingDeclareTime())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("高温补贴数据更新失败!");
        }
    }

    @Override
    public void delete(SubsidyHeatingDelDTO subsidyHeatingDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Subsidy> updateWrapper = new UpdateWrapper<Subsidy>()
                .lambda()
                .in(!CollectionUtils.isEmpty(subsidyHeatingDelDTO.getUids()), Subsidy:: getUid, subsidyHeatingDelDTO.getUids())
                .set(Subsidy:: getIsDeleted, System.currentTimeMillis())
                .set(Subsidy:: getUpdatedBy, currentUser.getPhone())
                .set(Subsidy:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("高温补贴数据删除失败!");
        }
    }
}

package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.AttendanceDTO;
import com.dfd.dto.AttendanceDelDTO;
import com.dfd.dto.AttendanceInfoDTO;
import com.dfd.dto.AttendanceMonInfoDTO;
import com.dfd.entity.Attendance;
import com.dfd.entity.Item;
import com.dfd.entity.ItemMember;
import com.dfd.entity.User;
import com.dfd.mapper.AttendanceMapper;
import com.dfd.mapper.ItemMapper;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.service.AttendanceService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.PageResult;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.AttendanceMonDataVO;
import com.dfd.vo.AttendanceMonInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 16:24
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemMemberMapper itemMemberMapper;

    @Override
    public PageResult<AttendanceInfoVO> info(AttendanceInfoDTO attendanceInfoDTO) {
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceInfoDTO.getItemUid()), Attendance:: getItemUid, attendanceInfoDTO.getItemUid())
                .eq(attendanceInfoDTO.getYear() !=null, Attendance:: getYear, attendanceInfoDTO.getYear())
                .eq(attendanceInfoDTO.getMonth() !=null, Attendance:: getMonth, attendanceInfoDTO.getMonth())
                .eq(attendanceInfoDTO.getDay() !=null, Attendance:: getDay, attendanceInfoDTO.getDay());
        queryWrapper.orderByDesc(Attendance :: getCreatedTime);

        Page<Attendance> pageReq = new Page(attendanceInfoDTO.getCurrentPage(), attendanceInfoDTO.getPageSize());
        IPage<Attendance> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<AttendanceInfoVO> pageResult = new PageResult(page)
                .setRecords(convertToAttendanceInfoVO(page.getRecords()));
        return pageResult;
    }

    @Override
    public PageResult<AttendanceMonInfoVO> monInfo(AttendanceMonInfoDTO attendanceMonInfoDTO) {
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceMonInfoDTO.getItemUid()), Attendance:: getItemUid, attendanceMonInfoDTO.getItemUid())
                .eq(attendanceMonInfoDTO.getYear() !=null, Attendance:: getYear, attendanceMonInfoDTO.getYear())
                .eq(attendanceMonInfoDTO.getMonth() !=null, Attendance:: getMonth, attendanceMonInfoDTO.getMonth());
        queryWrapper.orderByDesc(Attendance :: getCreatedTime);

        Page<Attendance> pageReq = new Page(attendanceMonInfoDTO.getCurrentPage(), attendanceMonInfoDTO.getPageSize());
        IPage<Attendance> page = baseMapper.selectPage(pageReq, queryWrapper);
        return convertToAttendanceMonInfoVO(page);
    }

    private PageResult<AttendanceMonInfoVO> convertToAttendanceMonInfoVO(IPage<Attendance> page) {
        PageResult<AttendanceMonInfoVO> pageResult = new PageResult(page);
        List<Attendance> list = page.getRecords();
        if (CollectionUtils.isEmpty(list)) {
            return pageResult
                    .setTotalRecords(GlobalConstant.GLOBAL_Lon_ZERO)
                    .setTotalPages(GlobalConstant.GLOBAL_Lon_ZERO)
                    .setRecords(Collections.emptyList());
        }
        List<String> itemIdList = list.stream().map(Attendance::getItemUid).collect(Collectors.toList());
        List<Item> items = itemMapper.selectBatchIds(itemIdList);
        Map<Integer, String> itemNames = items.stream().collect(Collectors.toMap(Item::getId, Item::getItemName));

        List<String> itemMemIdList = list.stream().map(Attendance::getItemMemberUid).collect(Collectors.toList());
        List<ItemMember> itemMembers = itemMemberMapper.selectBatchIds(itemMemIdList);
        Map<Integer, String> itemMemberNames = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getName));
        Map<Integer, String> itemMemberNumbers = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getNumber));

        List<AttendanceMonInfoVO> result = new ArrayList<>();
        Map<String, List<Attendance>> attCollect = list.stream().collect(Collectors.groupingBy(o ->
                (o.getItemUid() + o.getItemMemberUid() + o.getYear() + o.getMonth()), Collectors.toList()));
        for(Map.Entry<String, List<Attendance>> entry : attCollect.entrySet()){
            List<Attendance> value = entry.getValue();
            AttendanceMonInfoVO infoVO = new AttendanceMonInfoVO();
            int duty = 0;
            int out = 0;
            List<AttendanceMonDataVO> dataVOList = new ArrayList<>();
            for(Attendance a : value){
                BeanUtil.copyProperties(a,infoVO);
                infoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(a.getItemUid()) : null)
                        .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(a.getItemMemberUid()) : null)
                        .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(a.getItemMemberUid()) : null);
                AttendanceMonDataVO dataVO = new AttendanceMonDataVO();
                BeanUtil.copyProperties(a, dataVO);
                if(a.getStatus() == 1){
                    duty++;
                }
                if (a.getStatus() == 2) {
                    out++;
                }
                dataVOList.add(dataVO);
            }
            infoVO.setDutyTotalDays(duty)
                    .setOutgoingTotalDays(out)
                    .setAttendanceMonDataVOList(dataVOList);
            result.add(infoVO);
        }
        return pageResult
                .setTotalRecords(result.size())
                .setTotalPages(PageResult.countTotalPage(result.size(),page.getSize()))
                .setRecords(result);
    }

    private List<AttendanceInfoVO> convertToAttendanceInfoVO(List<Attendance> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<String> itemIdList = list.stream().map(Attendance::getItemUid).collect(Collectors.toList());
        List<Item> items = itemMapper.selectBatchIds(itemIdList);
        Map<Integer, String> itemNames = items.stream().collect(Collectors.toMap(Item::getId, Item::getItemName));

        List<String> itemMemIdList = list.stream().map(Attendance::getItemMemberUid).collect(Collectors.toList());
        List<ItemMember> itemMembers = itemMemberMapper.selectBatchIds(itemMemIdList);
        Map<Integer, String> itemMemberNames = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getName));
        Map<Integer, String> itemMemberNumbers = itemMembers.stream().collect(Collectors.toMap(ItemMember::getId, ItemMember::getNumber));
        List<AttendanceInfoVO> result = list.stream().map(attendance -> {
            if(!Optional.ofNullable(attendance).isPresent()){
                throw new BusinessException("考勤数据为空");
            }
            AttendanceInfoVO attendanceInfoVO = new AttendanceInfoVO();
            BeanUtil.copyProperties(attendance,attendanceInfoVO);
            attendanceInfoVO.setItemName(!itemNames.isEmpty() ? itemNames.get(attendance.getItemUid()) : null)
                    .setName(!itemMemberNames.isEmpty() ? itemMemberNames.get(attendance.getItemMemberUid()) : null)
                    .setNumber(!itemMemberNumbers.isEmpty() ? itemMemberNumbers.get(attendance.getItemMemberUid()) : null);
            return attendanceInfoVO;
        }).collect(Collectors.toList());
        return result;
    }


    @Override
    public void add(AttendanceDTO attendanceInfoDTO) {
        User currentUser = UserRequest.getCurrentUser();
        Attendance attendance = new Attendance();
        BeanUtil.copyProperties(attendanceInfoDTO,attendance);
        attendance.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_INT_ZERO);
        int insert = baseMapper.insert(attendance);
        if (insert < 0) {
            throw new BusinessException("考勤状态保存失败");
        }
    }

    @Override
    public void update(AttendanceDTO attendanceInfoDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Attendance> updateWrapper = new UpdateWrapper<Attendance>()
                .lambda()
                .eq(StringUtils.isNotBlank(attendanceInfoDTO.getItemUid()), Attendance:: getItemUid, attendanceInfoDTO.getItemUid())
                .eq(StringUtils.isNotBlank(attendanceInfoDTO.getItemMemberUid()), Attendance:: getItemMemberUid, attendanceInfoDTO.getItemMemberUid())
                .set((attendanceInfoDTO.getYear()!=null), Attendance:: getYear, attendanceInfoDTO.getYear())
                .set((attendanceInfoDTO.getMonth()!=null), Attendance:: getMonth, attendanceInfoDTO.getMonth())
                .set((attendanceInfoDTO.getDay()!=null), Attendance:: getDay, attendanceInfoDTO.getDay())
                .set((attendanceInfoDTO.getStatus()!=null), Attendance:: getStatus, attendanceInfoDTO.getStatus())
                .set((attendanceInfoDTO.getOutgoingTotalDays()!=null), Attendance:: getOutgoingTotalDays, attendanceInfoDTO.getOutgoingTotalDays())
                .set((attendanceInfoDTO.getDutyTotalDays()!=null), Attendance:: getDutyTotalDays, attendanceInfoDTO.getDutyTotalDays())
                .set(Attendance:: getUpdatedBy, currentUser.getPhone())
                .set(Attendance:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("考勤状态更新失败");
        }
    }

    @Override
    public void delete(AttendanceDelDTO attendanceDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Attendance> updateWrapper = new UpdateWrapper<Attendance>()
                .lambda()
                .eq(StringUtils.isNotBlank(attendanceDelDTO.getItemUid()), Attendance:: getItemUid, attendanceDelDTO.getItemUid())
                .in(!CollectionUtils.isEmpty(attendanceDelDTO.getItemMemberIds()), Attendance:: getItemMemberUid, attendanceDelDTO.getItemMemberIds())
                .set(Attendance:: getIsDeleted, System.currentTimeMillis())
                .set(Attendance:: getUpdatedBy, currentUser.getPhone())
                .set(Attendance:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("考勤状态删除失败");
        }
    }

}

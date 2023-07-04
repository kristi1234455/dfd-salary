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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @date 2023/6/12 16:24
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;
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
                .eq(attendanceInfoDTO.getDay() !=null, Attendance:: getDay, attendanceInfoDTO.getDay())
                //todo：没有考勤状态的人
//                .no( Attendance:: getStatus, attendanceInfoDTO.getDay())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
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
                .eq(attendanceMonInfoDTO.getMonth() !=null, Attendance:: getMonth, attendanceMonInfoDTO.getMonth())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
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
    public void add(AttendanceDayDTO attendanceDTO) {
        LambdaQueryWrapper<Attendance> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(StringUtils.isNotBlank(attendanceDTO.getItemUid()), Attendance:: getItemUid, attendanceDTO.getItemUid())
                .eq(StringUtils.isNotBlank(attendanceDTO.getItemMemberUid()), Attendance:: getItemMemberUid, attendanceDTO.getItemMemberUid())
                .eq(attendanceDTO.getYear() !=null, Attendance:: getYear, attendanceDTO.getYear())
                .eq(attendanceDTO.getMonth() !=null, Attendance:: getMonth, attendanceDTO.getMonth())
                .eq(attendanceDTO.getDay() !=null, Attendance:: getDay, attendanceDTO.getDay())
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        if(baseMapper.exists(queryWrapper)){
            throw new BusinessException("添加失败，该用户考勤数据已经存在！");
        }
        User currentUser = UserRequest.getCurrentUser();
        Attendance attendance = new Attendance();
        BeanUtil.copyProperties(attendanceDTO,attendance);
        attendance.setUid(UUIDUtil.getUUID32Bits())
                .setCreatedBy(currentUser.getPhone())
                .setUpdatedBy(currentUser.getPhone())
                .setCreatedTime(new Date())
                .setUpdatedTime(new Date())
                .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
        boolean b = this.saveOrUpdate(attendance);
        if (!b) {
            throw new BusinessException("考勤状态保存失败");
        }
    }

    @Override
    public void update(AttendanceDTO attendanceDTO) {
        List<Attendance> params = new ArrayList<>();
        List<AttendanceDataDTO> dataList = attendanceDTO.getAttendanceDataDTOList();
        if(CollectionUtils.isEmpty(dataList)){
            throw new BusinessException("更新失败，入参中考勤日期和状态缺失！");
        }
        User currentUser = UserRequest.getCurrentUser();
        dataList.stream().forEach(e ->{
            Attendance attendance = new Attendance();
            BeanUtil.copyProperties(attendanceDTO,attendance);
            attendance.setDay(e.getDay())
                    .setStatus(e.getStatus())
                    .setUid(e.getUid())
                    .setUpdatedBy(currentUser.getPhone())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            params.add(attendance);
        });
        attendanceMapper.updateByItemUid(params);
    }

    @Override
    public void delete(AttendanceDelDTO attendanceDelDTO) {
        User currentUser = UserRequest.getCurrentUser();
        LambdaUpdateWrapper<Attendance> updateWrapper = new UpdateWrapper<Attendance>()
                .lambda()
                .in(!CollectionUtils.isEmpty(attendanceDelDTO.getUids()), Attendance:: getUid, attendanceDelDTO.getUids())
                .set(Attendance:: getIsDeleted, System.currentTimeMillis())
                .set(Attendance:: getUpdatedBy, currentUser.getPhone())
                .set(Attendance:: getUpdatedTime, new Date());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("考勤状态删除失败");
        }
    }
}

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
import com.dfd.dto.AttendanceDTO;
import com.dfd.dto.AttendanceDelDTO;
import com.dfd.dto.AttendanceInfoDTO;
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
import com.dfd.vo.AttendanceInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
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
                .eq(ObjectUtils.isNotEmpty(attendanceInfoDTO.getYear()), Attendance:: getYear, attendanceInfoDTO.getYear())
                .eq(ObjectUtils.isNotEmpty(attendanceInfoDTO.getMonth()), Attendance:: getMonth, attendanceInfoDTO.getMonth());
        queryWrapper.orderByDesc(Attendance :: getCreatedTime);

        Page<Attendance> pageReq = new Page(attendanceInfoDTO.getCurrentPage(), attendanceInfoDTO.getPageSize());
        IPage<Attendance> page = baseMapper.selectPage(pageReq, queryWrapper);
        PageResult<AttendanceInfoVO> pageResult = new PageResult(page);
        pageResult.setRecords(convertToBannerVO(page.getRecords()));
        return pageResult;
    }

    private List<AttendanceInfoVO> convertToBannerVO(List<Attendance> list) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<AttendanceInfoVO> result = list.stream().map(attendance -> {
            AttendanceInfoVO attendanceInfoVO = new AttendanceInfoVO();
            BeanUtil.copyProperties(attendance,attendanceInfoVO);
            Item item = StringUtils.isNotEmpty(attendance.getItemUid()) ? itemMapper.selectById(attendance.getItemUid()) : null;
            attendanceInfoVO.setItemName(ObjectUtils.isNotEmpty(item) ? item.getItemName() : null);
            ItemMember itemMember = StringUtils.isNotEmpty(attendance.getItemUid()) ? itemMemberMapper.selectById(attendance.getItemMemberUid()) : null;
            attendanceInfoVO.setName(ObjectUtils.isNotEmpty(itemMember) ? itemMember.getName() : null);
            attendanceInfoVO.setNumber(ObjectUtils.isNotEmpty(itemMember) ? itemMember.getNumber() : null);
            return attendanceInfoVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void add(AttendanceDTO attendanceInfoDTO) {
        User currentUser = UserRequest.getCurrentUser();
        Attendance attendance = new Attendance();
        BeanUtil.copyProperties(attendanceInfoDTO,attendance);
        attendance.setCreatedBy(currentUser.getPhone());
        attendance.setUpdatedBy(currentUser.getPhone());
//        attendance.setCreatedTime(new Date());
//        attendance.setUpdatedTime(new Date());
        int insert = baseMapper.insert(attendance);
        if (insert < 0) {
            throw new BusinessException("考勤状态保存失败");
        }
    }

    @Override
    public void update(AttendanceDTO attendanceInfoDTO) {

    }

    @Override
    public void delete(AttendanceDelDTO attendanceDelDTO) {
        LambdaUpdateWrapper<Attendance> updateWrapper = new UpdateWrapper<Attendance>().lambda()
                .eq(StringUtils.isNotBlank(attendanceDelDTO.getItemUid()), Attendance:: getItemUid, attendanceDelDTO.getItemUid())
                .in(!CollectionUtils.isEmpty(attendanceDelDTO.getItemMemberIds()), Attendance:: getItemMemberUid, attendanceDelDTO.getItemMemberIds())
                .set(Attendance:: getIsDeleted, System.currentTimeMillis());
        boolean update = this.update(updateWrapper);
        if (!update) {
            throw new BusinessException("考勤状态更新失败");
        }
    }

}

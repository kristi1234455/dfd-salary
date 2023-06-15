package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.AttendanceDTO;
import com.dfd.dto.AttendanceDelDTO;
import com.dfd.dto.AttendanceInfoDTO;
import com.dfd.entity.Attendance;
import com.dfd.mapper.AttendanceMapper;
import com.dfd.service.AttendanceService;
import com.dfd.utils.PageResult;
import com.dfd.vo.AttendanceInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/12 16:24
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements AttendanceService {

    @Override
    public PageResult<AttendanceInfoVO> info(AttendanceInfoDTO attendanceInfoDTO) {
        return null;
    }

    @Override
    public void add(AttendanceDTO attendanceInfoDTO) {

    }

    @Override
    public void update(AttendanceDTO attendanceInfoDTO) {

    }

    @Override
    public void delete(AttendanceDelDTO attendanceDelDTO) {

    }

}

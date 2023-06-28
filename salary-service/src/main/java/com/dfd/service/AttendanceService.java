package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.Attendance;
import com.dfd.utils.PageResult;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.AttendanceMonInfoVO;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
public interface AttendanceService extends IService<Attendance> {

    PageResult<AttendanceInfoVO> info(AttendanceInfoDTO attendanceInfoDTO);

    PageResult<AttendanceMonInfoVO> monInfo(AttendanceMonInfoDTO attendanceMonInfoDTO);

    void add(AttendanceDayDTO attendanceInfoDTO);

    void update(AttendanceDTO attendanceInfoDTO);

    void delete(AttendanceDelDTO attendanceDelDTO);


}

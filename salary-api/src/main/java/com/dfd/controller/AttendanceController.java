package com.dfd.controller;

import com.dfd.dto.AttendanceDTO;
import com.dfd.dto.AttendanceDelDTO;
import com.dfd.dto.AttendanceInfoDTO;
import com.dfd.dto.AttendanceMonInfoDTO;
import com.dfd.service.AttendanceService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.AttendanceMonInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
@Api(value = "考勤管理", tags = {"用于考勤管理的相关接口"})
@RestController
@RequestMapping("attendance")
@CrossOrigin
@Slf4j
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @ApiOperation(value = "获取项目人员每天考勤状态", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<AttendanceInfoVO>> info(@RequestBody @Valid AttendanceInfoDTO attendanceInfoDTO){
        return DFDResult.sucess(attendanceService.info(attendanceInfoDTO));
    }

    @ApiOperation(value = "获取项目人员月度考勤汇总", httpMethod = "POST")
    @PostMapping("/month/info")
    public DFDResult<PageResult<AttendanceMonInfoVO>> monInfo(@RequestBody @Valid AttendanceMonInfoDTO attendanceMonInfoDTO){
        return DFDResult.sucess(attendanceService.monInfo(attendanceMonInfoDTO));
    }

    @ApiOperation(value = "新增项目的人员考勤状态", httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult add(@RequestBody @Valid AttendanceDTO attendanceInfoDTO){
        attendanceService.add(attendanceInfoDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新项目的人员考勤状态", httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult update(@RequestBody @Valid AttendanceDTO attendanceInfoDTO){
        attendanceService.update(attendanceInfoDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除项目的人员考勤状态", httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult delete(@RequestBody @Valid AttendanceDelDTO attendanceDelDTO){
        attendanceService.delete(attendanceDelDTO);
        return DFDResult.sucess();
    }

}

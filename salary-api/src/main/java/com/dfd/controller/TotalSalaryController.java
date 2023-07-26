package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.AttendanceService;
import com.dfd.service.CheckListService;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.AttendanceInfoVO;
import com.dfd.vo.AttendanceMonInfoVO;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
@Api(value = "工资汇总", tags = {"用于工资汇总管理的相关接口"})
@RestController
@RequestMapping("total/salary")
@CrossOrigin
@Slf4j
public class TotalSalaryController {

    @Autowired
    private TotalSalaryService totalSalaryService;

    @ApiOperation(value = "获取工资汇总相关数据", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<TotalSalaryInfoVO> info(@RequestBody @Valid TotalSalaryInfoDTO totalSalaryInfoDTO){
        return DFDResult.sucess(totalSalaryService.info(totalSalaryInfoDTO));
    }

}

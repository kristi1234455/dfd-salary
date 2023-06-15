package com.dfd.controller;

import com.dfd.dto.PerformanceSalaryDTO;
import com.dfd.dto.PerformanceSalaryDelDTO;
import com.dfd.dto.PerformanceSalaryInfoDTO;
import com.dfd.service.PerformanceSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.PerformanceSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/12 16:51
 */
@Api(value = "绩效工资管理", tags = {"用于绩效管理的相关接口"})
@RestController
@RequestMapping("performance/salary")
@CrossOrigin
public class PerformanceSalaryController {

    @Autowired
    private PerformanceSalaryService performanceSalaryService;

    @ApiOperation(value = "获取绩效基本工资", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<PerformanceSalaryInfoVO>> info(@RequestBody @Valid PerformanceSalaryInfoDTO performanceSalaryInfoDTO){
        return DFDResult.sucess(performanceSalaryService.info(performanceSalaryInfoDTO));
    }

    @ApiOperation(value = "增加绩效基本工资", httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult add(@RequestBody @Valid PerformanceSalaryDTO performanceSalaryDTO){
        performanceSalaryService.add(performanceSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新绩效基本工资", httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult update(@RequestBody @Valid PerformanceSalaryDTO performanceSalaryDTO){
        performanceSalaryService.update(performanceSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除绩效基本工资", httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult delete(@RequestBody @Valid PerformanceSalaryDelDTO performanceSalaryDelDTO){
        performanceSalaryService.delete(performanceSalaryDelDTO);
        return DFDResult.sucess();
    }

}

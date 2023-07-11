package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.DesignSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.DesignSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/9 15:05
 */
@RestController
@RequestMapping("design/salary")
@Api(value = "设计工资申报", tags = "用于设计工资申报的管理接口")
@CrossOrigin
public class DesignSalaryController {

    @Autowired
    private DesignSalaryService designSalaryService;

    @ApiOperation(value = "获取设计工资信息", httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<DesignSalaryInfoVO>> info(@RequestBody @Valid DesignSalaryInfoDTO designSalaryInfoDTO){
        return DFDResult.sucess(designSalaryService.info(designSalaryInfoDTO));
    }

    @PostMapping("add")
    @ApiOperation(value = "新增设计工资信息", httpMethod = "POST")
    public DFDResult add(@RequestBody @Valid DesignSalaryAddDTO designSalaryDTO){
        designSalaryService.add(designSalaryDTO);
        return DFDResult.sucess();
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑设计工资信息", httpMethod = "POST")
    public DFDResult update(@RequestBody @Valid DesignSalaryDTO designSalaryDTO){
        designSalaryService.update(designSalaryDTO);
        return DFDResult.sucess();
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除设计工资信息", httpMethod = "POST")
    public DFDResult delete(@RequestBody @Valid DesignSalaryDelDTO designSalaryDelDTO){
        designSalaryService.delete(designSalaryDelDTO);
        return DFDResult.sucess();
    }
}

package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.SubsidyService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyOutInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
@RestController
@RequestMapping("subsidy/out")
@Api(value = "加班补助申报",tags = "用于加班补助申报管理接口")
@CrossOrigin
public class SubsidyOutController {

    @Autowired
    private SubsidyService subsidyService;

    @ApiOperation(value = "获取驻外工资信息",httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<SubsidyOutInfoVO>> info(@RequestBody @Valid SubsidyOutInfoDTO subsidyOutInfoDTO){
        return DFDResult.sucess(subsidyService.info(subsidyOutInfoDTO));
    }

    @ApiOperation(value = "新增驻外工资信息",httpMethod = "POST")
    @PostMapping("add")
    public DFDResult add(@RequestBody @Valid SubsidyOutDTO subsidyOutDTO){
        subsidyService.add(subsidyOutDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新驻外工资信息",httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid SubsidyOutDTO subsidyOutDTO){
        subsidyService.update(subsidyOutDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除驻外工资信息",httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid SubsidyOutDelDTO subsidyOutDelDTO){
        subsidyService.delete(subsidyOutDelDTO);
        return DFDResult.sucess();
    }

}

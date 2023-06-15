package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.SubsidyService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyNightInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/14 11:10
 */
@RestController
@RequestMapping("subsidy/night")
@Api(value = "夜班补助申报",tags = "用于夜班补助申报管理接口")
@CrossOrigin
public class SubsidyNightController {

    @Autowired
    private SubsidyService subsidyService;

    @ApiOperation(value = "获取夜班工资信息",httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<SubsidyNightInfoVO>> info(@RequestBody @Valid SubsidyNightInfoDTO subsidyNightInfoDTO){
        return DFDResult.sucess(subsidyService.info(subsidyNightInfoDTO));
    }

    @ApiOperation(value = "新增夜班工资信息",httpMethod = "POST")
    @PostMapping("add")
    public DFDResult add(@RequestBody @Valid SubsidyNightDTO subsidyNightDTO){
        subsidyService.add(subsidyNightDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新夜班工资信息",httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid SubsidyNightDTO subsidyNightDTO){
        subsidyService.update(subsidyNightDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除夜班工资信息",httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid SubsidyNightDelDTO subsidyNightDelDTO){
        subsidyService.delete(subsidyNightDelDTO);
        return DFDResult.sucess();
    }
}

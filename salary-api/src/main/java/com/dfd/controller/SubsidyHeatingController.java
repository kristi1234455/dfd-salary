package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.CheckListService;
import com.dfd.service.SubsidyService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.SubsidyHeatingInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/14 14:54
 */
@RestController
@RequestMapping("subsidy/heating")
@Api(value = "高温补助申报",tags = "用于高温补助申报管理接口")
@CrossOrigin
public class SubsidyHeatingController {

    @Autowired
    private SubsidyService subsidyService;

    @ApiOperation(value = "获取高温工资信息",httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<SubsidyHeatingInfoVO>> info(@RequestBody @Valid SubsidyHeatingInfoDTO subsidyHeatingInfoDTO){
        return DFDResult.sucess(subsidyService.info(subsidyHeatingInfoDTO));
    }

    @ApiOperation(value = "新增高温工资信息",httpMethod = "POST")
    @PostMapping("add")
    public DFDResult add(@RequestBody @Valid SubsidyHeatingAddDTO subsidyHeatingDTO){
        subsidyService.add(subsidyHeatingDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新高温工资信息",httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid SubsidyHeatingDTO subsidyHeatingDTO){
        subsidyService.update(subsidyHeatingDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除高温工资信息",httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid SubsidyHeatingDelDTO subsidyHeatingDelDTO){
        subsidyService.delete(subsidyHeatingDelDTO);
        return DFDResult.sucess();
    }

    @Autowired
    private CheckListService checkListService;

    @ApiOperation(value = "审核：提交审核流程", httpMethod = "POST")
    @PostMapping("/audit/part/submit")
    public DFDResult partSubmit(@RequestBody @Valid CheckListPartSubmitDTO partSubmitDTO){
        checkListService.partSubmit(partSubmitDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "审核：获取当前项目的审核流程", httpMethod = "POST")
    @PostMapping("/audit/part/info")
    public DFDResult<List<CheckListPartInfoVO>> partInfo(@RequestBody @Valid CheckListPartInfoDTO partInfoDTO){
        return DFDResult.sucess(checkListService.partInfo(partInfoDTO));
    }
}

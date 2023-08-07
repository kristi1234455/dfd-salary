package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.CheckListService;
import com.dfd.service.SubsidyOvertimeService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
@RestController
@RequestMapping("subsidy/overtime")
@Api(value = "加班补助申报",tags = "用于加班补助申报管理接口")
@CrossOrigin
public class SubsidyOvertimeController {

    @Autowired
    private SubsidyOvertimeService subsidyOvertimeService;

    @ApiOperation(value = "获取加班工资信息",httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<SubsidyOvertimeInfoVO>> info(@RequestBody @Valid SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO){
        return DFDResult.sucess(subsidyOvertimeService.info(subsidyOvertimeInfoDTO));
    }

    @ApiOperation(value = "新增加班工资信息",httpMethod = "POST")
    @PostMapping("add")
    public DFDResult add(@RequestBody @Valid SubsidyOvertimeAddDTO subsidyOvertimeDTO){
        subsidyOvertimeService.add(subsidyOvertimeDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新加班工资信息",httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid SubsidyOvertimeDTO subsidyOvertimeDTO){
        subsidyOvertimeService.update(subsidyOvertimeDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除加班工资信息",httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid SubsidyOvertimeDelDTO subsidyOvertimeDelDTO){
        subsidyOvertimeService.delete(subsidyOvertimeDelDTO);
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

package com.dfd.controller;

import com.dfd.dto.SubsidyOvertimeDTO;
import com.dfd.dto.SubsidyOvertimeDelDTO;
import com.dfd.dto.SubsidyOvertimeInfoDTO;
import com.dfd.service.SubsidyService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
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
@RequestMapping("subsidy/overtime")
@Api(value = "加班补助申报",tags = "用于加班补助申报管理接口")
@CrossOrigin
public class SubsidyOvertimeController {

    @Autowired
    private SubsidyService subsidyService;

    @ApiOperation(value = "获取加班工资信息",httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<SubsidyOvertimeInfoVO>> info(@RequestBody @Valid SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO){
        return DFDResult.sucess(subsidyService.info(subsidyOvertimeInfoDTO));
    }

    @ApiOperation(value = "新增加班工资信息",httpMethod = "POST")
    @PostMapping("add")
    public DFDResult add(@RequestBody @Valid SubsidyOvertimeDTO subsidyOvertimeDTO){
        subsidyService.add(subsidyOvertimeDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新加班工资信息",httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid SubsidyOvertimeDTO subsidyOvertimeDTO){
        subsidyService.update(subsidyOvertimeDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除加班工资信息",httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid SubsidyOvertimeDelDTO subsidyOvertimeDelDTO){
        subsidyService.delete(subsidyOvertimeDelDTO);
        return DFDResult.sucess();
    }

}

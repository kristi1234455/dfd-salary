package com.dfd.controller;

import com.dfd.dto.ScientificSalaryAddDTO;
import com.dfd.dto.ScientificSalaryDTO;
import com.dfd.dto.ScientificSalaryDelDTO;
import com.dfd.dto.ScientificSalaryInfoDTO;
import com.dfd.entity.ScientificSalary;
import com.dfd.service.ScientificSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.ScientificSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/9 11:45
 */
@RestController
@Api(value = "科研工资申报", tags = "用于科研工资申报的管理接口")
@RequestMapping("scientific/salary")
@CrossOrigin
public class ScientificSalaryController {

    @Autowired
    private ScientificSalaryService scientificSalaryService;

    @PostMapping("info")
    @ApiOperation(value = "获取科研工资信息", httpMethod = "POST")
    public DFDResult<PageResult<ScientificSalaryInfoVO>> info(@RequestBody @Valid ScientificSalaryInfoDTO scientificSalaryInfoDTO){
        return DFDResult.sucess(scientificSalaryService.info(scientificSalaryInfoDTO));
    }

    @PostMapping("add")
    @ApiOperation(value = "新增科研工资信息", httpMethod = "POST")
    public DFDResult add(@RequestBody @Valid ScientificSalaryAddDTO scientificSalaryDTO){
        scientificSalaryService.add(scientificSalaryDTO);
        return DFDResult.sucess();
    }

    @PostMapping("update")
    @ApiOperation(value = "编辑科研工资信息", httpMethod = "POST")
    public DFDResult update(@RequestBody @Valid ScientificSalaryDTO scientificSalaryDTO){
        scientificSalaryService.update(scientificSalaryDTO);
        return DFDResult.sucess();
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除科研工资信息", httpMethod = "POST")
    public DFDResult delete(@RequestBody @Valid ScientificSalaryDelDTO scientificSalaryDelDTO){
        scientificSalaryService.delete(scientificSalaryDelDTO);
        return DFDResult.sucess();
    }
}

package com.dfd.controller;

import com.dfd.dto.ItemSalaryDTO;
import com.dfd.dto.ItemSalaryDelDTO;
import com.dfd.dto.ItemSalaryInfoDTO;
import com.dfd.service.ItemSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/12 10:28
 */
@Api(value = "项目岗位工资管理", tags = {"用于项目岗位工资管理的相关接口"})
@RestController
@RequestMapping("item/salary")
@CrossOrigin
public class ItemSalaryController {

    @Autowired
    private ItemSalaryService itemSalaryService;

    @ApiOperation(value = "根据项目的人员岗位基本工资", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<ItemSalaryInfoVO>> info(@RequestBody @Valid ItemSalaryInfoDTO itemSalaryInfoDTO){
        return DFDResult.sucess(itemSalaryService.info(itemSalaryInfoDTO));
    }

    @ApiOperation(value = "新增项目的人员岗位基本工资", httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult add(@RequestBody @Valid ItemSalaryDTO itemSalaryDTO){
        itemSalaryService.add(itemSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新项目的人员岗位基本工资", httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult update(@RequestBody @Valid ItemSalaryDTO itemSalaryDTO){
        itemSalaryService.update(itemSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除项目的人员岗位基本工资", httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult delete(@RequestBody @Valid ItemSalaryDelDTO itemSalaryDelDTO){
        itemSalaryService.delete(itemSalaryDelDTO);
        return DFDResult.sucess();
    }
}

package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.CheckListService;
import com.dfd.service.ItemSalaryService;
import com.dfd.service.ItemTotalSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.ItemSalaryInfoVO;
import com.dfd.vo.TechnicalFeeInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 10:28
 */
@Api(value = "项目岗位工资管理", tags = {"用于项目岗位工资管理的相关接口"})
@RestController
@RequestMapping("item/salary")
@CrossOrigin
public class ItemTotalSalaryController {

    @Autowired
    private ItemTotalSalaryService itemTotalSalaryService;


    @ApiOperation(value = "返回项目的技术管理费", httpMethod = "POST")
    @PostMapping("info/technical")
    public DFDResult<PageResult<TechnicalFeeInfoVO>> infoTechnical(@RequestBody @Valid TechnicalFeeInfoDTO technicalFeeInfoDTO){
        return DFDResult.sucess(itemTotalSalaryService.infoTechnical(technicalFeeInfoDTO));
    }
}

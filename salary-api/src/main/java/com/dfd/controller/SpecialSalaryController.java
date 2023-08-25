package com.dfd.controller;

import com.dfd.dto.*;
import com.dfd.service.CheckListService;
import com.dfd.vo.CheckListPartInfoVO;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/9 16:59
 */
@RestController
@RequestMapping("special")
@Api(value = "专岗津贴申报", tags = "用于专岗津贴申报的管理接口")
@CrossOrigin
public class SpecialSalaryController {

    @Autowired
    private TotalSalaryService totalSalaryService;

    @ApiOperation(value = "获取专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<SpecialInfoVO>> infoSpecial(@RequestBody @Valid SpecialInfoDTO specialInfoDTO){
        return DFDResult.sucess(totalSalaryService.infoSpecial(specialInfoDTO));
    }

    @ApiOperation(value = "新增专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("/add")
    public DFDResult addSpecial(@RequestBody @Valid SpecialAddDTO speciaAddlDTO){
        totalSalaryService.addSpecial(speciaAddlDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult updateSpecial(@RequestBody @Valid SpecialDTO specialVO){
        totalSalaryService.updateSpecial(specialVO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("/del")
    public DFDResult delSpecial(@RequestBody @Valid SpecialDelDTO specialDelDTO){
        totalSalaryService.delSpecial(specialDelDTO);
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

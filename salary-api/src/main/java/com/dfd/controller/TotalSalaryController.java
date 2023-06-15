package com.dfd.controller;

import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/9 16:59
 */
@RestController
@RequestMapping("total")
@Api(value = "工资汇总申报", tags = "用于工资汇总申报的管理接口")
@CrossOrigin
public class TotalSalaryController {

    @Autowired
    private TotalSalaryService totalSalaryService;

    @ApiOperation(value = "获取专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("special/info")
    public DFDResult<PageResult<SpecialInfoVO>> infoSpecial(@RequestBody @Valid SpecialInfoDTO specialInfoDTO){
        return DFDResult.sucess(totalSalaryService.infoSpecial(specialInfoDTO));
    }

    @ApiOperation(value = "新增专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("special/add")
    public DFDResult addSpecial(@RequestBody @Valid SpecialDTO specialDTO){
        totalSalaryService.addSpecial(specialDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "更新专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("special/update")
    public DFDResult updateSpecial(@RequestBody @Valid SpecialDTO specialVO){
        totalSalaryService.updateSpecial(specialVO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除专岗津贴工资信息", httpMethod = "POST")
    @PostMapping("special/del")
    public DFDResult delSpecial(@RequestBody @Valid SpecialDelDTO specialDelDTO){
        totalSalaryService.delSpecial(specialDelDTO);
        return DFDResult.sucess();
    }

}

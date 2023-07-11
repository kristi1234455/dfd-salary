package com.dfd.controller;

import com.dfd.dto.BidSalaryAddDTO;
import com.dfd.dto.BidSalaryDelDTO;
import com.dfd.dto.BidSalaryDTO;
import com.dfd.dto.BidSalaryInfoDTO;
import com.dfd.service.BidSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.BidSalaryInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yy
 * @date 2023/6/8 17:26
 */
@Api(value = "投标工资申报", tags = "用于投标工资申报的接口")
@RestController
@RequestMapping("bid/salary")
@CrossOrigin
public class BidSalaryController {

    @Autowired
    private BidSalaryService bidSalaryService;

    @ApiOperation(value = "获取当前项目的投标工资", httpMethod = "POST")
    @PostMapping("info")
    public DFDResult<PageResult<BidSalaryInfoVO>> info(@RequestBody @Valid BidSalaryInfoDTO bidSalaryInfoDTO){
        return DFDResult.sucess(bidSalaryService.info(bidSalaryInfoDTO));
    }

    @ApiOperation(value = "保存投标工资", httpMethod = "POST")
    @PostMapping("save")
    public DFDResult sava(@RequestBody @Valid BidSalaryAddDTO bidSalaryDTO){
        bidSalaryService.save(bidSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "编辑更新投标工资", httpMethod = "POST")
    @PostMapping("update")
    public DFDResult update(@RequestBody @Valid BidSalaryDTO bidSalaryDTO){
        bidSalaryService.update(bidSalaryDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除投标工资", httpMethod = "POST")
    @PostMapping("delete")
    public DFDResult delete(@RequestBody @Valid BidSalaryDelDTO bidSalaryDelDTO){
        bidSalaryService.delete(bidSalaryDelDTO);
        return DFDResult.sucess();
    }
}

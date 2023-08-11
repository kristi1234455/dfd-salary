package com.dfd.controller;

import cn.hutool.core.util.PageUtil;
import com.dfd.dto.*;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.ExcelUtils;
import com.dfd.utils.PageResult;
import com.dfd.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 16:21
 */
@Api(value = "工资汇总", tags = {"用于工资汇总管理的相关接口"})
@RestController
@RequestMapping("total/salary")
@CrossOrigin
@Slf4j
public class TotalSalaryController {

    @Autowired
    private TotalSalaryService totalSalaryService;

    @ApiOperation(value = "获取工资综合汇总相关数据", httpMethod = "POST")
    @PostMapping("/summary/info")
    public DFDResult<PageResult<TotalSalaryInfoVO>> info(@RequestBody @Valid TotalSalaryInfoDTO totalSalaryInfoDTO){
        return DFDResult.sucess(totalSalaryService.info(totalSalaryInfoDTO));
    }

    @ApiOperation(value = "工资综合汇总数据导出", httpMethod = "POST")
    @PostMapping("/summary/export")
    public DFDResult<TotalSalarySummaryExportVO> exportSummarySalary(TotalSalaryInfoDTO totalSalaryInfoDTO, HttpServletResponse response){
        int totalSize = totalSalaryService.exportSummarySalaryCount(totalSalaryInfoDTO);
        int pageSize = 100;
        totalSalaryInfoDTO.setPageSize(pageSize);
        int pages = PageUtil.totalPage(totalSize,pageSize);
        if (totalSize > 0) {
            ExcelUtils.exportBigData(response, "工资汇总表",  TotalSalarySummaryExportVO.class,pageSize,pages,(i)->{
                totalSalaryInfoDTO.setCurrentPage(i);
                List<TotalSalarySummaryExportVO> data = totalSalaryService.exportSummarySalaryList(totalSalaryInfoDTO);
                return data;
            });
        }
        return DFDResult.sucess();
    }

    @ApiOperation(value = "获取工资清单汇总数据", httpMethod = "POST")
    @PostMapping("/info/payroll")
    public DFDResult<PageResult<TotalSalaryPayrollInfoVO>> infoPayroll(@RequestBody @Valid TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO){
        return DFDResult.sucess(totalSalaryService.infoPayroll(totalSalaryPayrollInfoDTO));
    }

    @ApiOperation(value = "工资清单表格导出", httpMethod = "POST")
    @GetMapping("export")
    public DFDResult exportSalary(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO, HttpServletResponse response){
        int totalSize = totalSalaryService.exportSalaryCount(totalSalaryPayrollInfoDTO);
        int pageSize = 100;
        totalSalaryPayrollInfoDTO.setPageSize(pageSize);
        int pages = PageUtil.totalPage(totalSize,pageSize);
        if (totalSize > 0) {
            ExcelUtils.exportBigData(response, "工资清单",  TotalSalaryPayrollExportVO.class,pageSize,pages,(i)->{
                totalSalaryPayrollInfoDTO.setCurrentPage(i);
                List<TotalSalaryPayrollExportVO> data = totalSalaryService.exportSalaryList(totalSalaryPayrollInfoDTO);
                return data;
            });
        }
        return DFDResult.sucess();
    }

}

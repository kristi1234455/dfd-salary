package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.TotalSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.*;

import java.util.List;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface TotalSalaryService extends IService<TotalSalary> {

    PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO);

    void addSpecial(SpecialAddDTO speciaAddlDTO);

    void updateSpecial(SpecialDTO specialVO);

    void delSpecial(SpecialDelDTO specialDelDTO);

    /**
     * 获取工资汇总相关数据
     * @param totalSalaryInfoDTO
     * @return
     */
    PageResult<TotalSalaryInfoVO> info(TotalSalaryInfoDTO totalSalaryInfoDTO);

    /**
     * 工资汇总总数据量
     * @param totalSalaryInfoDTO
     * @return
     */
    int exportSummarySalaryCount(TotalSalaryInfoDTO totalSalaryInfoDTO);

    /**
     * 获取工资汇总表格导出数据
     * @param totalSalaryInfoDTO
     * @return
     */
    List<TotalSalarySummaryExportVO> exportSummarySalaryList(TotalSalaryInfoDTO totalSalaryInfoDTO);

    /**
     * 获取工资清单汇总数据
     * @param totalSalaryPayrollInfoDTO
     * @return
     */
    PageResult<TotalSalaryPayrollInfoVO> infoPayroll(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);

    /**
     * 项目清单总数据量
     * @param totalSalaryPayrollInfoDTO
     * @return
     */
    int exportSalaryCount(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);

    /**
     * 获取项目清单表格导出数据
     * @param totalSalaryPayrollInfoDTO
     * @return
     */
    List<TotalSalaryPayrollExportVO> exportSalaryList(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);


}

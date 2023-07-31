package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.TotalSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;
import com.dfd.vo.TotalSalaryPayrollExportVO;
import com.dfd.vo.TotalSalaryPayrollInfoVO;

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

    TotalSalaryInfoVO info(TotalSalaryInfoDTO totalSalaryInfoDTO);

    PageResult<TotalSalaryPayrollInfoVO> infoPayroll(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);

    int exportSalaryCount(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);

    List<TotalSalaryPayrollExportVO> exportSalaryList(TotalSalaryPayrollInfoDTO totalSalaryPayrollInfoDTO);
}

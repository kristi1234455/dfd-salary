package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.PerformanceSalaryAddDTO;
import com.dfd.dto.PerformanceSalaryDTO;
import com.dfd.dto.PerformanceSalaryDelDTO;
import com.dfd.dto.PerformanceSalaryInfoDTO;
import com.dfd.entity.PerformanceSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.PerformanceSalaryInfoVO;

/**
 * @author yy
 * @date 2023/6/12 17:06
 */
public interface PerformanceSalaryService extends IService<PerformanceSalary> {
    PageResult<PerformanceSalaryInfoVO> info(PerformanceSalaryInfoDTO performanceSalaryInfoDTO);

    void add(PerformanceSalaryAddDTO performanceSalaryAddDTO);

    void update(PerformanceSalaryDTO performanceSalaryDTO);

    void delete(PerformanceSalaryDelDTO performanceSalaryDelDTO);
}

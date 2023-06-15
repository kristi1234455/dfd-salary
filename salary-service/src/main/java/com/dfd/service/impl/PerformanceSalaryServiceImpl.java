package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.PerformanceSalaryDTO;
import com.dfd.dto.PerformanceSalaryDelDTO;
import com.dfd.dto.PerformanceSalaryInfoDTO;
import com.dfd.entity.PerformanceSalary;
import com.dfd.mapper.PerformanceSalaryMapper;
import com.dfd.service.PerformanceSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.PerformanceSalaryInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/12 17:06
 */
@Service
public class PerformanceSalaryServiceImpl extends ServiceImpl<PerformanceSalaryMapper, PerformanceSalary> implements PerformanceSalaryService {

    @Override
    public PageResult<PerformanceSalaryInfoVO> info(PerformanceSalaryInfoDTO performanceSalaryInfoDTO) {
        return null;
    }

    @Override
    public void add(PerformanceSalaryDTO performanceSalaryDTO) {

    }

    @Override
    public void update(PerformanceSalaryDTO performanceSalaryDTO) {

    }

    @Override
    public void delete(PerformanceSalaryDelDTO performanceSalaryDelDTO) {

    }

}

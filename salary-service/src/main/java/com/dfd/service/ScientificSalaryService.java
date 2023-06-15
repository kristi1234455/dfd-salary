package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.ScientificSalaryDTO;
import com.dfd.dto.ScientificSalaryDelDTO;
import com.dfd.dto.ScientificSalaryInfoDTO;
import com.dfd.entity.ScientificSalary;
import com.dfd.mapper.ScientificSalaryMapper;
import com.dfd.utils.PageResult;
import com.dfd.vo.ScientificSalaryInfoVO;

/**
 * @author yy
 * @date 2023/6/9 11:50
 */
public interface ScientificSalaryService extends IService<ScientificSalary> {
    PageResult<ScientificSalaryInfoVO> info(ScientificSalaryInfoDTO scientificSalaryInfoDTO);

    void add(ScientificSalaryDTO scientificSalaryDTO);

    void update(ScientificSalaryDTO scientificSalaryDTO);

    void delete(ScientificSalaryDelDTO scientificSalaryDelDTO);
}

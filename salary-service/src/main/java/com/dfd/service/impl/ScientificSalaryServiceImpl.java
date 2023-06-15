package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.ScientificSalaryDTO;
import com.dfd.dto.ScientificSalaryDelDTO;
import com.dfd.dto.ScientificSalaryInfoDTO;
import com.dfd.entity.ScientificSalary;
import com.dfd.mapper.ScientificSalaryMapper;
import com.dfd.service.ScientificSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.ScientificSalaryInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/9 11:50
 */
@Service
public class ScientificSalaryServiceImpl extends ServiceImpl<ScientificSalaryMapper, ScientificSalary> implements ScientificSalaryService {

    @Autowired
    private ScientificSalaryMapper scientificSalaryMapper;

    @Override
    public PageResult<ScientificSalaryInfoVO> info(ScientificSalaryInfoDTO scientificSalaryInfoDTO) {
        return null;
    }

    @Override
    public void add(ScientificSalaryDTO scientificSalaryDTO) {

    }

    @Override
    public void update(ScientificSalaryDTO scientificSalaryDTO) {

    }

    @Override
    public void delete(ScientificSalaryDelDTO scientificSalaryDelDTO) {

    }
}

package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.DesignSalaryDTO;
import com.dfd.dto.DesignSalaryDelDTO;
import com.dfd.dto.DesignSalaryInfoDTO;
import com.dfd.entity.DesignSalary;
import com.dfd.mapper.DesignSalaryMapper;
import com.dfd.service.DesignSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.DesignSalaryInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/9 15:20
 */
@Service
public class DesignSalaryServiceImpl extends ServiceImpl<DesignSalaryMapper, DesignSalary> implements DesignSalaryService {

    @Override
    public PageResult<DesignSalaryInfoVO> info(DesignSalaryInfoDTO designSalaryInfoDTO) {
        return null;
    }

    @Override
    public void add(DesignSalaryDTO designSalaryDTO) {

    }

    @Override
    public void update(DesignSalaryDTO designSalaryDTO) {

    }

    @Override
    public void delete(DesignSalaryDelDTO designSalaryDelDTO) {

    }
}

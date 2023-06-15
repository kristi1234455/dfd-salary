package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.TotalSalary;
import com.dfd.mapper.TotalSalaryMapper;
import com.dfd.service.TotalSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/9 17:30
 */
@Service
public class TotalSalaryServiceImpl extends ServiceImpl<TotalSalaryMapper, TotalSalary> implements TotalSalaryService {

    @Override
    public PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO) {
        return null;
    }

    @Override
    public void addSpecial(SpecialDTO specialDTO) {

    }

    @Override
    public void updateSpecial(SpecialDTO specialVO) {

    }

    @Override
    public void delSpecial(SpecialDelDTO specialDelDTO) {

    }
}

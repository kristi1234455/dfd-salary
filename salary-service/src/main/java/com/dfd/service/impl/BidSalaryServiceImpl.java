package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.BidSalaryDelDTO;
import com.dfd.dto.BidSalaryDTO;
import com.dfd.dto.BidSalaryInfoDTO;
import com.dfd.entity.BidSalary;
import com.dfd.mapper.BidSalaryMapper;
import com.dfd.service.BidSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.BidSalaryInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/8 17:33
 */
@Service
public class BidSalaryServiceImpl extends ServiceImpl<BidSalaryMapper, BidSalary> implements BidSalaryService {

    @Autowired
    private BidSalaryMapper bidSalaryMapper;


    @Override
    public PageResult<BidSalaryInfoVO> info(BidSalaryInfoDTO bidSalaryInfoDTO) {
        return null;
    }

    @Override
    public void save(BidSalaryDTO bidSalaryDTO) {

    }

    @Override
    public void update(BidSalaryDTO bidSalaryDTO) {

    }

    @Override
    public void delete(BidSalaryDelDTO bidSalaryDelDTO) {

    }
}

package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.*;
import com.dfd.entity.Subsidy;
import com.dfd.mapper.SubsidyMapper;
import com.dfd.service.SubsidyService;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyHeatingInfoVO;
import com.dfd.vo.SubsidyNightInfoVO;
import com.dfd.vo.SubsidyOutInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
@Service
public class SubsidyServiceImpl extends ServiceImpl<SubsidyMapper, Subsidy> implements SubsidyService {
    @Override
    public PageResult<SubsidyOvertimeInfoVO> info(SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO) {
        return null;
    }

    @Override
    public void add(SubsidyOvertimeDTO subsidyOvertimeDTO) {

    }

    @Override
    public void update(SubsidyOvertimeDTO subsidyOvertimeDTO) {

    }

    @Override
    public void delete(SubsidyOvertimeDelDTO subsidyOvertimeDelDTO) {

    }

    @Override
    public PageResult<SubsidyNightInfoVO> info(SubsidyNightInfoDTO subsidyNightInfoDTO) {
        return null;
    }

    @Override
    public void add(SubsidyNightDTO subsidyNightDTO) {

    }

    @Override
    public void update(SubsidyNightDTO subsidyNightDTO) {

    }

    @Override
    public void delete(SubsidyNightDelDTO subsidyNightDelDTO) {

    }

    @Override
    public PageResult<SubsidyOutInfoVO> info(SubsidyOutInfoDTO subsidyOutInfoDTO) {
        return null;
    }

    @Override
    public void add(SubsidyOutDTO subsidyOutDTO) {

    }

    @Override
    public void update(SubsidyOutDTO subsidyOutDTO) {

    }

    @Override
    public void delete(SubsidyOutDelDTO subsidyOutDelDTO) {

    }

    @Override
    public PageResult<SubsidyHeatingInfoVO> info(SubsidyHeatingInfoDTO subsidyHeatingInfoDTO) {
        return null;
    }

    @Override
    public void add(SubsidyHeatingDTO subsidyHeatingDTO) {

    }

    @Override
    public void update(SubsidyHeatingDTO subsidyHeatingDTO) {

    }

    @Override
    public void delete(SubsidyHeatingDelDTO subsidyHeatingDelDTO) {

    }
}

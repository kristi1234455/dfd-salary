package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.Subsidy;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyHeatingInfoVO;
import com.dfd.vo.SubsidyNightInfoVO;
import com.dfd.vo.SubsidyOutInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
public interface SubsidyService extends IService <Subsidy>{

    PageResult<SubsidyOvertimeInfoVO> info(SubsidyOvertimeInfoDTO subsidyOvertimeInfoDTO);

    void add(SubsidyOvertimeDTO subsidyOvertimeDTO);

    void update(SubsidyOvertimeDTO subsidyOvertimeDTO);

    void delete(SubsidyOvertimeDelDTO subsidyOvertimeDelDTO);

    PageResult<SubsidyNightInfoVO> info(SubsidyNightInfoDTO subsidyNightInfoDTO);

    void add(SubsidyNightDTO subsidyNightDTO);

    void update(SubsidyNightDTO subsidyNightDTO);

    void delete(SubsidyNightDelDTO subsidyNightDelDTO);

    PageResult<SubsidyOutInfoVO> info(SubsidyOutInfoDTO subsidyOutInfoDTO);

    void add(SubsidyOutDTO subsidyOutDTO);

    void update(SubsidyOutDTO subsidyOutDTO);

    void delete(SubsidyOutDelDTO subsidyOutDelDTO);

    PageResult<SubsidyHeatingInfoVO> info(SubsidyHeatingInfoDTO subsidyHeatingInfoDTO);

    void add(SubsidyHeatingDTO subsidyHeatingDTO);

    void update(SubsidyHeatingDTO subsidyHeatingDTO);

    void delete(SubsidyHeatingDelDTO subsidyHeatingDelDTO);
}

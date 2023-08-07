package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyHeating;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyHeatingInfoVO;
import com.dfd.vo.SubsidyNightInfoVO;
import com.dfd.vo.SubsidyOutInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
public interface SubsidyHeatingService extends IService <SubsidyHeating>{

    PageResult<SubsidyHeatingInfoVO> info(SubsidyHeatingInfoDTO subsidyHeatingInfoDTO);

    void add(SubsidyHeatingAddDTO subsidyHeatingDTO);

    void update(SubsidyHeatingDTO subsidyHeatingDTO);

    void delete(SubsidyHeatingDelDTO subsidyHeatingDelDTO);
}

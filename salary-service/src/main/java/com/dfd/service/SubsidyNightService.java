package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.SubsidyNight;
import com.dfd.utils.PageResult;
import com.dfd.vo.SubsidyHeatingInfoVO;
import com.dfd.vo.SubsidyNightInfoVO;
import com.dfd.vo.SubsidyOutInfoVO;
import com.dfd.vo.SubsidyOvertimeInfoVO;

/**
 * @author yy
 * @date 2023/6/13 16:54
 */
public interface SubsidyNightService extends IService <SubsidyNight>{

    PageResult<SubsidyNightInfoVO> info(SubsidyNightInfoDTO subsidyNightInfoDTO);

    void add(SubsidyNightAddDTO subsidyNightDTO);

    void update(SubsidyNightDTO subsidyNightDTO);

    void delete(SubsidyNightDelDTO subsidyNightDelDTO);
}

package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.*;
import com.dfd.entity.ItemTotalSalary;
import com.dfd.entity.TotalSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;
import com.dfd.vo.TechnicalFeeInfoVO;
import com.dfd.vo.TotalSalaryInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface ItemTotalSalaryService extends IService<ItemTotalSalary> {

    PageResult<TechnicalFeeInfoVO> infoTechnical(TechnicalFeeInfoDTO technicalFeeInfoDTO);


    /**
     * 根据itemuid获取项目的技术管理费
     * @param uids
     * @return
     */
    Map<String, String> queryUsedTechnicalFeeByUids(List<String> uids);
}

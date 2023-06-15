package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.BidSalaryDelDTO;
import com.dfd.dto.BidSalaryDTO;
import com.dfd.dto.BidSalaryInfoDTO;
import com.dfd.entity.BidSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.BidSalaryInfoVO;

/**
 * @author yy
 * @date 2023/6/8 17:33
 */
public interface BidSalaryService extends IService<BidSalary> {

    PageResult<BidSalaryInfoVO> info(BidSalaryInfoDTO bidSalaryInfoDTO);

    void save(BidSalaryDTO bidSalaryDTO);

    void update(BidSalaryDTO bidSalaryDTO);

    void delete(BidSalaryDelDTO bidSalaryDelDTO);

}

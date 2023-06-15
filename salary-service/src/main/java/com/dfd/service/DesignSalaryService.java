package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.DesignSalaryDTO;
import com.dfd.dto.DesignSalaryDelDTO;
import com.dfd.dto.DesignSalaryInfoDTO;
import com.dfd.entity.DesignSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.DesignSalaryInfoVO;

/**
 * @author yy
 * @date 2023/6/9 15:19
 */
public interface DesignSalaryService extends IService<DesignSalary> {

    PageResult<DesignSalaryInfoVO> info(DesignSalaryInfoDTO designSalaryInfoDTO);

    void add(DesignSalaryDTO designSalaryDTO);

    void update(DesignSalaryDTO designSalaryDTO);

    void delete(DesignSalaryDelDTO designSalaryDelDTO);
}

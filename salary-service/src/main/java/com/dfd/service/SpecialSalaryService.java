package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.SpecialAddDTO;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.SpecialSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;

/**
 * @author yy
 * @date 2023/8/29 10:17
 */
public interface SpecialSalaryService  extends IService<SpecialSalary> {

    PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO);

    void addSpecial(SpecialAddDTO speciaAddlDTO);

    void updateSpecial(SpecialDTO specialVO);

    void delSpecial(SpecialDelDTO specialDelDTO);
}

package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.SpecialDTO;
import com.dfd.dto.SpecialDelDTO;
import com.dfd.dto.SpecialInfoDTO;
import com.dfd.entity.TotalSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.SpecialInfoVO;

/**
 * @author yy
 * @date 2023/6/9 17:26
 */
public interface TotalSalaryService extends IService<TotalSalary> {

    PageResult<SpecialInfoVO> infoSpecial(SpecialInfoDTO specialInfoDTO);

    void addSpecial(SpecialDTO specialDTO);

    void updateSpecial(SpecialDTO specialVO);

    void delSpecial(SpecialDelDTO specialDelDTO);
}

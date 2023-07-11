package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.ItemSalaryAddDTO;
import com.dfd.dto.ItemSalaryDTO;
import com.dfd.dto.ItemSalaryDelDTO;
import com.dfd.dto.ItemSalaryInfoDTO;
import com.dfd.entity.ItemSalary;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemSalaryInfoVO;

/**
 * @author yy
 * @date 2023/6/12 10:39
 */
public interface ItemSalaryService extends IService<ItemSalary> {

    PageResult<ItemSalaryInfoVO> info(ItemSalaryInfoDTO itemSalaryInfoDTO);

    void add(ItemSalaryAddDTO itemSalaryDTO);

    void update(ItemSalaryDTO itemSalaryDTO);

    void delete(ItemSalaryDelDTO itemSalaryDelDTO);
}

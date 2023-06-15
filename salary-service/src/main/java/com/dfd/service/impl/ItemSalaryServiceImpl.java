package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.dto.ItemSalaryDTO;
import com.dfd.dto.ItemSalaryDelDTO;
import com.dfd.dto.ItemSalaryInfoDTO;
import com.dfd.entity.ItemSalary;
import com.dfd.mapper.ItemSalaryMapper;
import com.dfd.service.ItemSalaryService;
import com.dfd.utils.PageResult;
import com.dfd.vo.ItemSalaryInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/6/12 10:39
 */
@Service
public class ItemSalaryServiceImpl extends ServiceImpl<ItemSalaryMapper, ItemSalary> implements ItemSalaryService {

    @Autowired
    private ItemSalaryMapper itemSalaryMapper;

    @Override
    public PageResult<ItemSalaryInfoVO> info(ItemSalaryInfoDTO itemSalaryInfoDTO) {
        return null;
    }

    @Override
    public void add(ItemSalaryDTO itemSalaryDTO) {

    }

    @Override
    public void update(ItemSalaryDTO itemSalaryDTO) {

    }

    @Override
    public void delete(ItemSalaryDelDTO itemSalaryDelDTO) {

    }
}

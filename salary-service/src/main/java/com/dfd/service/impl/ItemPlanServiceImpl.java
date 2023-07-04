package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.entity.ItemPlan;
import com.dfd.entity.ItemSalary;
import com.dfd.mapper.ItemPlanMapper;
import com.dfd.mapper.ItemSalaryMapper;
import com.dfd.service.ItemPlanService;
import com.dfd.service.ItemSalaryService;
import org.springframework.stereotype.Service;

/**
 * @author yy
 * @date 2023/7/4 17:53
 */
@Service
public class ItemPlanServiceImpl extends ServiceImpl<ItemPlanMapper, ItemPlan> implements ItemPlanService {

}

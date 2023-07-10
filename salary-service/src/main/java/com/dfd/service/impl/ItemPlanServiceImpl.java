package com.dfd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.entity.ItemMember;
import com.dfd.entity.ItemPlan;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.mapper.ItemPlanMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.ItemPlanService;
import org.springframework.stereotype.Service;

@Service
public class ItemPlanServiceImpl extends ServiceImpl<ItemPlanMapper, ItemPlan> implements ItemPlanService {
}

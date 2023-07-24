package com.dfd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.ItemPlanDTO;
import com.dfd.entity.ItemPlan;
import com.dfd.entity.ItemSalary;

import java.util.List;

/**
 * @author yy
 * @date 2023/7/4 17:53
 */
public interface ItemPlanService extends IService<ItemPlan> {

    void updatePlanByItemId(String itemUid, List<ItemPlanDTO> nPlanDTOs);
}

package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.ItemPlanDTO;
import com.dfd.entity.ItemMember;
import com.dfd.entity.ItemPlan;
import com.dfd.entity.User;
import com.dfd.mapper.ItemMemberMapper;
import com.dfd.mapper.ItemPlanMapper;
import com.dfd.service.ItemMemberService;
import com.dfd.service.ItemPlanService;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemPlanServiceImpl extends ServiceImpl<ItemPlanMapper, ItemPlan> implements ItemPlanService {
    @Override
    public void updatePlanByItemId(String itemUid, List<ItemPlanDTO> nPlanDTOs) {
        User currentUser = UserRequest.getCurrentUser();

        LambdaUpdateWrapper<ItemPlan> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(itemUid), ItemPlan:: getItemUid, itemUid)
                .eq(ItemPlan::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemPlan> oPlans = list(wrapper);

        oPlans.stream().forEach(e ->{
            e.setRemarks(String.valueOf(new Date()))
                    .setIsDeleted(String.valueOf(System.currentTimeMillis()))
                    .setUpdatedBy(currentUser.getNumber())
                    .setUpdatedTime(new Date());
        });
        if(CollectionUtil.isNotEmpty(oPlans)){
            boolean delete = updateBatchById(oPlans);
            if(!delete){
                throw new BusinessException("项目删除原来策划系数失败！");
            }
        }

        //批量新增
        List<ItemPlan> upItemPlans = new ArrayList<>();
        nPlanDTOs.stream().forEach(e ->{
            ItemPlan var = new ItemPlan();
            BeanUtil.copyProperties(e,var);
            var.setUid(UUIDUtil.getUUID32Bits())
                    .setItemUid(itemUid)
                    .setItemMemberUid(e.getItemMemberUid())
                    .setCreatedBy(currentUser.getNumber())
                    .setCreatedTime(new Date())
                    .setUpdatedBy(currentUser.getNumber())
                    .setUpdatedTime(new Date())
                    .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
            upItemPlans.add(var);
        });
        boolean update = saveBatch(upItemPlans);
        if ( !update) {
            throw new BusinessException("根据项目id更新项目人员数据失败!");
        }
    }
}

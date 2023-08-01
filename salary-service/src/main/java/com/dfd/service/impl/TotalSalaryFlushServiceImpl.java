package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dfd.constant.GlobalConstant;
import com.dfd.entity.*;
import com.dfd.service.*;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.TotalSalaryItemInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TotalSalaryFlushServiceImpl implements TotalSalaryFlushService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private TotalSalaryRoomService totalSalaryRoomService;


    public void flushTotalSalary() {
        //todo


    }

    public void flushTotalSalaryItem(){
//        List<String> itemMemberUids = list.stream().map(TotalSalary::getItemMemberUid).collect(Collectors.toList());
//
//        LambdaQueryWrapper<ItemMember> itemMemberWrap = new LambdaQueryWrapper();
//        itemMemberWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemMember::getMemberUid, itemMemberUids)
//                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<ItemMember> itemMembers = itemMemberService.list(itemMemberWrap);
////        Map<String, List<ObjVo>> modelMap = itemMembers.stream().collect(Collectors.groupingBy(ObjVo::getId));
////        Map<String, List<String>> memberItemMap = itemMembers.stream().collect(Collectors.toMap(ItemMember::getMemberUid, ItemMember::getItemUid));
//
//        List<String> itemUids = itemMembers.stream().map(ItemMember::getItemUid).collect(Collectors.toList());
//        LambdaQueryWrapper<Item> itemWrapper = new LambdaQueryWrapper();
//        itemWrapper.in(CollectionUtil.isNotEmpty(itemUids), Item::getUid, itemUids)
//                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<Item> items = itemService.list(itemWrapper);
//        Map<String, Item> itemUidItemsMap = items.stream().collect(Collectors.toMap(Item::getUid, o->o));
//
//        LambdaQueryWrapper<ItemSalary> itemSalaryWrapper = new LambdaQueryWrapper();
//        itemSalaryWrapper.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemSalary::getItemMemberUid, itemMemberUids)
//                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<ItemSalary> itemSalaries = itemSalaryService.list(itemSalaryWrapper);
//        Map<String, ItemSalary> itemMemberUidsItemSalaryMap = itemSalaries.stream().collect(Collectors.toMap(ItemSalary::getItemMemberUid, o->o));
//
//        LambdaQueryWrapper<PerformanceSalary> performanceSalaryWrapper = new LambdaQueryWrapper();
//        performanceSalaryWrapper.in(CollectionUtil.isNotEmpty(itemMemberUids), PerformanceSalary::getItemMemberUid, itemMemberUids)
//                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<PerformanceSalary> performanceSalaries = performanceSalaryService.list(performanceSalaryWrapper);
//        Map<String, PerformanceSalary> itemMemberUidsPerformanceSalaryMap = performanceSalaries.stream().collect(Collectors.toMap(PerformanceSalary::getItemMemberUid, o->o));
//
//        LambdaQueryWrapper<DesignSalary> designSalaryWrapper = new LambdaQueryWrapper();
//        designSalaryWrapper.in(CollectionUtil.isNotEmpty(itemMemberUids), DesignSalary::getItemMemberUid, itemMemberUids)
//                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<DesignSalary> designSalaries = designSalaryService.list(designSalaryWrapper);
//        Map<String, DesignSalary> itemMemberUidsDesignSalaryMap = designSalaries.stream().collect(Collectors.toMap(DesignSalary::getItemMemberUid, o->o));
//
//        LambdaQueryWrapper<BidSalary> bidSalaryWrapper = new LambdaQueryWrapper();
//        bidSalaryWrapper.in(CollectionUtil.isNotEmpty(itemMemberUids), BidSalary::getItemMemberUid, itemMemberUids)
//                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<BidSalary> bidSalaries = bidSalaryService.list(bidSalaryWrapper);
//        Map<String, BidSalary> itemMemberUidsbidSalaryMap = bidSalaries.stream().collect(Collectors.toMap(BidSalary::getItemMemberUid, o->o));
//
//        //todo:查询添加当前时间 item的名字 mysql添加项目和项目人员主键
//        Map<String, List<TotalSalaryItemInfoVO>> result = new HashMap<>();
////        Map<String, List<Item>> itemMemberUidItemMap = new HashMap<>();
//        for(TotalSalary totalSalary : list){
//            String memberUid = totalSalary.getItemMemberUid();
//            if(result.containsKey(memberUid)){
//                List<TotalSalaryItemInfoVO> resultList = result.get(memberUid);
//                TotalSalaryItemInfoVO resultVO = new TotalSalaryItemInfoVO();
//                BeanUtils.copyProperties();
//                resultList.add(itemUidItemsMap.get(memberUid));
//                itemMemberUidItemMap.put(memberUid,itemList);
//            }else{
//                List<Item> itemList = new ArrayList<>();
//                itemList.add(itemUidItemsMap.get(memberUid));
//                itemMemberUidItemMap.put(memberUid,itemList);
//            }
//
//        }
//
//        if(ObjectUtil.isNotEmpty(itemMember) && StringUtils.isNotEmpty(itemMember.getMemberUid())){
//            Item item = itemUidItemsMap.get(itemMember.getItemUid());
//            TotalSalaryItemInfoVO itemInfoVO = new TotalSalaryItemInfoVO();
//            BeanUtil.copyProperties(item, itemInfoVO);
//            result.put(itemMember.getMemberUid(),)
//        }
//        return itemNames;
    }

    public void flushTotalSalaryRoom(){
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(queryWrapper);

        LambdaQueryWrapper<TotalSalaryRoom> wrapper = new LambdaQueryWrapper();
        wrapper.eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalaryRoom> totalSalaryRoomList = totalSalaryRoomService.list(wrapper);

        List<Item> addElements = itemList.stream()
                .filter(obj1 -> totalSalaryRoomList.stream().noneMatch(obj2 -> obj1.getUid().equals(obj2.getItemUid())))
                .collect(Collectors.toList());

        List<Item> updateElements = itemList.stream()
                .filter(obj1 -> !totalSalaryRoomList.stream().noneMatch(obj2 -> obj1.getUid().equals(obj2.getItemUid())))
                .collect(Collectors.toList());

        User currentUser = UserRequest.getCurrentUser();
        if(!CollectionUtils.isEmpty(addElements)){
            List<TotalSalaryRoom> collect = addElements.stream().map(var -> {
                TotalSalaryRoom totalSalaryRoom = new TotalSalaryRoom();
                BeanUtil.copyProperties(var, totalSalaryRoom);
                totalSalaryRoom.setId(null)
                        .setUid(UUIDUtil.getUUID32Bits())
                        .setItemUid(var.getUid())
                        .setCreatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date()).setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date());
                return totalSalaryRoom;
            }).collect(Collectors.toList());

            boolean b = totalSalaryRoomService.saveBatch(collect);
            if (!b) {
                throw new BusinessException("项目工资添加失败!");
            }
        }
        if(!CollectionUtils.isEmpty(updateElements)){
            List<TotalSalaryRoom> collect = updateElements.stream().map(var -> {
                TotalSalaryRoom totalSalaryRoom = new TotalSalaryRoom();
                BeanUtil.copyProperties(var, totalSalaryRoom);
                totalSalaryRoom.setId(null)
                        .setUid(null)
                        .setItemUid(var.getUid())
                        .setCreatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date()).setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date());
                return totalSalaryRoom;
            }).collect(Collectors.toList());

            int update = totalSalaryRoomService.updateByItemUid(collect);
            if (update<=0) {
                throw new BusinessException("项目工资更新失败!");
            }
        }

    }

}

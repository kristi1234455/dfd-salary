package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dfd.constant.GlobalConstant;
import com.dfd.entity.*;
import com.dfd.enums.ItemPropertiesEnum;
import com.dfd.enums.ItemStageEnum;
import com.dfd.service.*;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.UUIDUtil;
import com.dfd.vo.TotalSalaryItemInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TotalSalaryFlushServiceImpl implements TotalSalaryFlushService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMemberService itemMemberService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemSalaryService itemSalaryService;

    @Autowired
    private PerformanceSalaryService performanceSalaryService;

    @Autowired
    private DesignSalaryService designSalaryService;

    @Autowired
    private BidSalaryService bidSalaryService;

    @Autowired
    private ScientificSalaryService scientificSalaryService;

    @Autowired
    private SubsidyService subsidyService;

    @Autowired
    private TotalSalaryService totalSalaryService;

    @Autowired
    private TotalSalaryItemService totalSalaryItemService;

    @Autowired
    private TotalSalaryRoomService totalSalaryRoomService;


    @Override
    public void flushMonthTotalSalary() {
        LambdaQueryWrapper<TotalSalary> totalSalaryWrapper = new LambdaQueryWrapper();
        totalSalaryWrapper.likeRight(TotalSalary:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrapper);

        User currentUser = UserRequest.getCurrentUser();

        //项目人员的添加和更新
        LambdaQueryWrapper<Item> itemQueryWrapper = new LambdaQueryWrapper();
        itemQueryWrapper.ne(Item::getItemStage, ItemStageEnum.STAGE_FINISH.getCode())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(itemQueryWrapper);
        Map<String, Item> itemUidItem = itemList.stream().collect(Collectors.toMap(Item::getUid, o -> o));

        List<String> itemUids = itemList.stream().map(Item::getUid).collect(Collectors.toList());
        LambdaQueryWrapper<ItemMember> itemMemberWrap = new LambdaQueryWrapper();
        itemMemberWrap.in(CollectionUtil.isNotEmpty(itemUids), ItemMember::getItemUid, itemUids)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> itemMemberList = itemMemberService.list(itemMemberWrap);
        if(CollectionUtils.isEmpty(itemMemberList)){
            return;
        }

        List<String> memberUids = itemMemberList.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        LambdaQueryWrapper<Member> memberWrap = new LambdaQueryWrapper();
        memberWrap.in(CollectionUtil.isNotEmpty(memberUids), Member::getUid, memberUids)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Member> memberList = memberService.list(memberWrap);
        Map<String, Member> memberUidMember = memberList.stream().collect(Collectors.toMap(Member::getUid, o -> o));

        List<ItemMember> addItemMember = itemMemberList.stream()
                .filter(obj1 -> totalSalaryList.stream().noneMatch(obj2 -> obj1.getMemberUid().equals(obj2.getItemMemberUid())))
                .collect(Collectors.toList());
        List<TotalSalary> updateItemMember = totalSalaryList.stream()
                .filter(obj1 -> !addItemMember.stream().noneMatch(obj2 -> obj1.getItemMemberUid().equals(obj2.getMemberUid())))
                .collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addItemMember)){
            List<TotalSalary> collect = addItemMember.stream().map(itemMember -> {
                Item item = itemUidItem.get(itemMember.getItemUid());
                Member member = memberUidMember.get(itemMember.getMemberUid());
                TotalSalary totalSalary = new TotalSalary();
                totalSalary.setId(null)
                        .setUid(UUIDUtil.getUUID32Bits())
                        .setItemUid(item.getUid())
                        .setItemName(item.getItemName())
                        .setItemMemberUid(itemMember.getMemberUid())
                        .setRoom(member.getRoom())
                        .setDepartment(member.getDepartment())
                        .setNumber(member.getNumber())
                        .setName(member.getName())
                        .setCreatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date())
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                return totalSalary;
            }).collect(Collectors.toList());
            boolean b = totalSalaryService.saveBatch(collect);
            if (!b) {
                throw new BusinessException("项目人员信息添加失败!");
            }
        }
        if(!CollectionUtils.isEmpty(updateItemMember)){
            List<TotalSalary> collect = updateItemMember.stream().map(oldTotalSalary -> {
                Item item = itemUidItem.get(oldTotalSalary.getItemUid());
                Member member = memberUidMember.get(oldTotalSalary.getItemMemberUid());
                TotalSalary totalSalary = new TotalSalary();
                totalSalary.setItemName(item.getItemName())
                        .setRoom(member.getRoom())
                        .setDepartment(member.getDepartment())
                        .setNumber(member.getNumber())
                        .setName(member.getName())
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date());
                return totalSalary;
            }).collect(Collectors.toList());
            boolean b = totalSalaryService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目人员信息更新失败!");
            }
        }

        //更新项目基本工资
        List<String> itemMemberUids = itemMemberList.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        LambdaQueryWrapper<ItemSalary> itemSalaryWrap = new LambdaQueryWrapper();
        itemSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemSalary::getItemMemberUid, itemMemberUids)
                .likeRight( ItemSalary:: getDeclareTime, DateUtil.getYM())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaryWrapList = itemSalaryService.list(itemSalaryWrap);
        Map<String, BigDecimal> itemMemberUidItemSalary = null;
        if(!CollectionUtils.isEmpty(itemSalaryWrapList)){
            itemMemberUidItemSalary = itemSalaryWrapList.stream()
                    .collect(Collectors.toMap(ItemSalary::getItemMemberUid, ItemSalary::getDeclareGrant));
        }

        //更新绩效工资
        LambdaQueryWrapper<PerformanceSalary> performanceSalaryWrap = new LambdaQueryWrapper();
        performanceSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), PerformanceSalary::getItemMemberUid, itemMemberUids)
                .likeRight( PerformanceSalary:: getDeclareTime, DateUtil.getYM())
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<PerformanceSalary> performanceSalaryList = performanceSalaryService.list(performanceSalaryWrap);
        Map<String, BigDecimal> itemMemberUidPerformanceSalary = null;
        if(!CollectionUtils.isEmpty(performanceSalaryList)){
            itemMemberUidPerformanceSalary = performanceSalaryList.stream()
                    .collect(Collectors.toMap(PerformanceSalary::getItemMemberUid, PerformanceSalary::getPerformanceSalary));
        }

        //更新设计工资
        LambdaQueryWrapper<DesignSalary> designSalaryWrap = new LambdaQueryWrapper();
        designSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), DesignSalary::getItemMemberUid, itemMemberUids)
                .likeRight( DesignSalary:: getDeclareTime, DateUtil.getYM())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<DesignSalary> designSalaryList = designSalaryService.list(designSalaryWrap);
        Map<String, BigDecimal> itemMemberUidDesignSalary = null;
        if(!CollectionUtils.isEmpty(designSalaryList)){
            itemMemberUidDesignSalary = designSalaryList.stream()
                    .collect(Collectors.toMap(DesignSalary::getItemMemberUid, DesignSalary::getSubtotal));
        }

        //更新投标工资
        LambdaQueryWrapper<BidSalary> bidSalaryWrap = new LambdaQueryWrapper();
        bidSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), BidSalary::getItemMemberUid, itemMemberUids)
                .likeRight( BidSalary:: getDeclareTime, DateUtil.getYM())
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<BidSalary> bidSalaryList = bidSalaryService.list(bidSalaryWrap);
        Map<String, BigDecimal> itemMemberUidBidSalary = null;
        if (!CollectionUtils.isEmpty(bidSalaryList)){
            itemMemberUidBidSalary = bidSalaryList.stream()
                    .collect(Collectors.toMap(BidSalary::getItemMemberUid, BidSalary::getBidFee));
        }

        //更新科研工资
        LambdaQueryWrapper<ScientificSalary> scientificWrap = new LambdaQueryWrapper();
        scientificWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ScientificSalary::getItemMemberUid, itemMemberUids)
                .likeRight( ScientificSalary:: getDeclareTime, DateUtil.getYM())
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ScientificSalary> scientificList = scientificSalaryService.list(scientificWrap);
        Map<String, BigDecimal> itemMemberUidScientificSalary = null;
        if(!CollectionUtils.isEmpty(scientificList)){
            itemMemberUidScientificSalary = scientificList.stream()
                    .collect(Collectors.toMap(ScientificSalary::getItemMemberUid, ScientificSalary::getSubtotal));
        }

        //更新补助
        LambdaQueryWrapper<Subsidy> subsidyWrap = new LambdaQueryWrapper();
        subsidyWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), Subsidy::getItemMemberUid, itemMemberUids)
                .likeRight( Subsidy:: getOutDeclareTime, DateUtil.getYM())
                .likeRight( Subsidy:: getNightDeclareTime, DateUtil.getYM())
                .likeRight( Subsidy:: getOvertimeDeclareTime, DateUtil.getYM())
                .likeRight( Subsidy:: getHeatingDeclareTime, DateUtil.getYM())
                .eq(Subsidy::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Subsidy> subsidyList = subsidyService.list(subsidyWrap);
        Map<String, BigDecimal> itemMemberUidOutSubsidy = null;
        Map<String, BigDecimal> itemMemberUidNightSubsidy =null;
        Map<String, BigDecimal> itemMemberUidOvertimeSubsidy = null;
        Map<String, BigDecimal> itemMemberUidHeatingSubsidy = null;
        if(!CollectionUtils.isEmpty(subsidyList)){
            itemMemberUidOutSubsidy  = subsidyList.stream().collect(Collectors.toMap(Subsidy::getItemMemberUid, Subsidy::getOutSubsidy));
            itemMemberUidNightSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getItemMemberUid, Subsidy::getNightSubsidy));
            itemMemberUidOvertimeSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getItemMemberUid, Subsidy::getOvertimeSubsidy));
            itemMemberUidHeatingSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getItemMemberUid, Subsidy::getHeatingSubsidy));
        }

        if(!CollectionUtils.isEmpty(totalSalaryList)){
            List<TotalSalary> collect = new ArrayList<>();
            for(TotalSalary var :totalSalaryList){
                TotalSalary totalSalary = new TotalSalary();
                totalSalary.setId(var.getId())
                        .setDeclareGrant(itemMemberUidItemSalary!=null ? itemMemberUidItemSalary.get(var.getItemMemberUid()): totalSalary.getDeclareGrant())
                        .setPerformanceTotal(itemMemberUidPerformanceSalary!=null ? itemMemberUidPerformanceSalary.get(var.getItemMemberUid()): totalSalary.getPerformanceTotal())
                        .setDesignTotal(itemMemberUidDesignSalary != null ? itemMemberUidDesignSalary.get(var.getItemMemberUid()) : totalSalary.getDesignTotal())
                        .setTenderTotal(itemMemberUidBidSalary!=null ? itemMemberUidBidSalary.get(var.getItemMemberUid()) : totalSalary.getTenderTotal())
                        .setScientificTotal(itemMemberUidScientificSalary !=null ? itemMemberUidScientificSalary.get(var.getItemMemberUid()): totalSalary.getScientificTotal())
                        .setOutSubsidy(itemMemberUidOutSubsidy !=null ? itemMemberUidOutSubsidy.get(var.getItemMemberUid()) : totalSalary.getOutSubsidy())
                        .setNightSubsidy(itemMemberUidNightSubsidy!=null ? itemMemberUidNightSubsidy.get(var.getItemMemberUid()) :totalSalary.getNightSubsidy())
                        .setOvertimeSubsidy(itemMemberUidOvertimeSubsidy!=null ? itemMemberUidOvertimeSubsidy.get(var.getItemMemberUid()) : totalSalary.getOvertimeSubsidy())
                        .setHeatingSubsidy(itemMemberUidHeatingSubsidy!=null ? itemMemberUidHeatingSubsidy.get(var.getItemMemberUid()): totalSalary.getHeatingSubsidy())
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date());
                collect.add(totalSalary);
            }
            boolean b = totalSalaryService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目人员工资更新失败!");
            }
        }

    }

    @Override
    public void flushMonthTotalSalaryItem(){
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(queryWrapper);

        List<String> itemUids = itemList.stream().map(Item::getUid).collect(Collectors.toList());
        LambdaQueryWrapper<ItemMember> itemMemberWrap = new LambdaQueryWrapper();
        itemMemberWrap.in(CollectionUtil.isNotEmpty(itemUids), ItemMember::getItemUid, itemUids)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> itemMemberUids = itemMemberService.list(itemMemberWrap);

        LambdaQueryWrapper<TotalSalary> totalSalaryWrap = new LambdaQueryWrapper();
        totalSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), TotalSalary::getItemMemberUid, itemMemberUids)
                .likeRight( TotalSalary:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrap);

        LambdaQueryWrapper<TotalSalaryItem> totalSalaryItemWrap = new LambdaQueryWrapper();
        totalSalaryItemWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), TotalSalaryItem::getItemMemberUid, itemMemberUids)
                .likeRight( TotalSalaryItem:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalaryItem::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalaryItem> totalSalaryItemList = totalSalaryItemService.list(totalSalaryItemWrap);

        List<TotalSalary> addTotalSalary = totalSalaryList.stream()
                .filter(obj1 -> totalSalaryItemList.stream().noneMatch(obj2 -> obj1.getItemMemberUid().equals(obj2.getItemMemberUid())))
                .collect(Collectors.toList());
        List<TotalSalary> updateTotalSalary = totalSalaryList.stream()
                .filter(obj1 -> !totalSalaryItemList.stream().noneMatch(obj2 -> obj1.getItemMemberUid().equals(obj2.getItemMemberUid())))
                .collect(Collectors.toList());

        User currentUser = UserRequest.getCurrentUser();

        if(!CollectionUtils.isEmpty(addTotalSalary)){
            List<TotalSalary> collect = addTotalSalary.stream().map(var -> {
                TotalSalary totalSalary = new TotalSalary();
                BeanUtil.copyProperties(var, totalSalary);
                totalSalary.setId(null)
                        .setUid(UUIDUtil.getUUID32Bits())
                        .setItemUid(var.getUid())
                        .setCreatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date())
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                return totalSalary;
            }).collect(Collectors.toList());
            boolean b = totalSalaryService.saveBatch(collect);
            if (!b) {
                throw new BusinessException("项目汇总工资添加失败!");
            }
        }

        if(!CollectionUtils.isEmpty(updateTotalSalary)){
            List<TotalSalary> collect = updateTotalSalary.stream().map(var -> {
                TotalSalary totalSalary = new TotalSalary();
                BeanUtil.copyProperties(var, totalSalary);
                totalSalary
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date());
                return totalSalary;
            }).collect(Collectors.toList());
            boolean b = totalSalaryService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目汇总工资更新失败!");
            }
        }

    }

    @Override
    public void flushMonthTotalSalaryRoom(){
        //todo：total-salary到total-salary-room
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
                        .setCreatedTime(new Date())
                        .setUpdatedBy(currentUser.getNumber())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);;
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
                        .setUpdatedBy(currentUser.getNumber())
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

package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    private SubsidyOutService subsidyOutService;

    @Autowired
    private SubsidyHeatingService subsidyHeatingService;

    @Autowired
    private SubsidyNightService subsidyNightService;

    @Autowired
    private SubsidyOvertimeService subsidyOvertimeService;

    @Autowired
    private TotalSalaryService totalSalaryService;

    @Autowired
    private TotalSalaryItemService totalSalaryItemService;

    @Autowired
    private TotalSalaryRoomService totalSalaryRoomService;


    @Override
    public void flushMonthTotalSalary() {
        User currentUser = UserRequest.getCurrentUser();
        flushMonthTotalSalary(currentUser.getNumber());
    }

    @Override
    public void flushMonthTotalSalary(String currentNumber) {
        //项目人员的添加和更新
        LambdaQueryWrapper<Item> itemQueryWrapper = new LambdaQueryWrapper();
        itemQueryWrapper.ne(Item::getItemStage, ItemStageEnum.STAGE_FINISH.getCode())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(itemQueryWrapper);
        if(CollectionUtil.isEmpty(itemList)){
            return;
        }
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
        if(CollectionUtils.isEmpty(memberList)){
            return;
        }
        Map<String, Member> memberUidMember = memberList.stream().collect(Collectors.toMap(Member::getUid, o -> o));

        List<String> itemUidItemMemberUidDate = new ArrayList<>();
        Map<String,ItemMember> uidItemMember = new HashMap<>();
        for(ItemMember itemMember : itemMemberList){
            String key = itemMember.getItemUid() + itemMember.getMemberUid() + DateUtil.getYM();
            itemUidItemMemberUidDate.add(key);
            uidItemMember.put(key,itemMember);

        }
        LambdaQueryWrapper<TotalSalary> totalSalaryWrapper = new LambdaQueryWrapper();
        totalSalaryWrapper.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), TotalSalary::getUid, itemUidItemMemberUidDate)
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrapper);

        List<ItemMember> addItemMember = new ArrayList<>();
        List<TotalSalary> updateItemMember = new ArrayList<>();
        Map<String, TotalSalary> uidTotalSalary = totalSalaryList.stream().collect(Collectors.toMap(TotalSalary::getUid, o -> o));
        for(Map.Entry<String, ItemMember> entry : uidItemMember.entrySet()){
            if(uidTotalSalary.containsKey(entry.getKey())){
                updateItemMember.add(uidTotalSalary.get(entry.getKey()));
            }else{
                addItemMember.add(entry.getValue());
            }
        }
        if(CollectionUtils.isEmpty(addItemMember) && CollectionUtils.isEmpty(updateItemMember)){
            return;
        }

        //更新项目基本工资
        LambdaQueryWrapper<ItemSalary> itemSalaryWrap = new LambdaQueryWrapper();
        itemSalaryWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), ItemSalary::getUid, itemUidItemMemberUidDate)
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaryWrapList = itemSalaryService.list(itemSalaryWrap);
        Map<String, ItemSalary> itemMemberUidItemSalary = null;
        if(!CollectionUtils.isEmpty(itemSalaryWrapList)){
            itemMemberUidItemSalary = itemSalaryWrapList.stream().collect(Collectors.toMap(ItemSalary::getUid, o->o));
        }

        //更新绩效工资
        LambdaQueryWrapper<PerformanceSalary> performanceSalaryWrap = new LambdaQueryWrapper();
        performanceSalaryWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), PerformanceSalary::getUid, itemUidItemMemberUidDate)
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<PerformanceSalary> performanceSalaryList = performanceSalaryService.list(performanceSalaryWrap);
        Map<String, PerformanceSalary> itemMemberUidPerformanceSalary = null;
        if(!CollectionUtils.isEmpty(performanceSalaryList)){
            itemMemberUidPerformanceSalary = performanceSalaryList.stream().collect(Collectors.toMap(PerformanceSalary::getUid, o->o));
        }

        //更新设计工资
        LambdaQueryWrapper<DesignSalary> designSalaryWrap = new LambdaQueryWrapper();
        designSalaryWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), DesignSalary::getUid, itemUidItemMemberUidDate)
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<DesignSalary> designSalaryList = designSalaryService.list(designSalaryWrap);
        Map<String, DesignSalary> itemMemberUidDesignSalary = null;
        if(!CollectionUtils.isEmpty(designSalaryList)){itemMemberUidDesignSalary = designSalaryList.stream().collect(Collectors.toMap(DesignSalary::getUid, o->o));
        }

        //更新投标工资
        LambdaQueryWrapper<BidSalary> bidSalaryWrap = new LambdaQueryWrapper();
        bidSalaryWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), BidSalary::getUid, itemUidItemMemberUidDate)
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<BidSalary> bidSalaryList = bidSalaryService.list(bidSalaryWrap);
        Map<String, BidSalary> itemMemberUidBidSalary = null;
        if (!CollectionUtils.isEmpty(bidSalaryList)){
            itemMemberUidBidSalary = bidSalaryList.stream().collect(Collectors.toMap(BidSalary::getUid, o->o));
        }

        //更新科研工资
        LambdaQueryWrapper<ScientificSalary> scientificWrap = new LambdaQueryWrapper();
        scientificWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), ScientificSalary::getUid, itemUidItemMemberUidDate)
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ScientificSalary> scientificList = scientificSalaryService.list(scientificWrap);
        Map<String, ScientificSalary> itemMemberUidScientificSalary = null;
        if(!CollectionUtils.isEmpty(scientificList)){
            itemMemberUidScientificSalary = scientificList.stream().collect(Collectors.toMap(ScientificSalary::getUid, o->o));
        }

        //更新高温补助
        LambdaQueryWrapper<SubsidyHeating> subsidyHeatingWrap = new LambdaQueryWrapper();
        subsidyHeatingWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), SubsidyHeating::getUid, itemUidItemMemberUidDate)
                .eq(SubsidyHeating::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyHeating> subsidyHeatingList = subsidyHeatingService.list(subsidyHeatingWrap);
        Map<String, SubsidyHeating> itemMemberUidHeatingSubsidy = null;
        if(!CollectionUtils.isEmpty(subsidyHeatingList)){
            itemMemberUidHeatingSubsidy  = subsidyHeatingList.stream().collect(Collectors.toMap(SubsidyHeating::getUid, o->o));
        }

        //更新夜班补助
        LambdaQueryWrapper<SubsidyNight> subsidyNightWrap = new LambdaQueryWrapper();
        subsidyNightWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), SubsidyNight::getUid, itemUidItemMemberUidDate)
                .eq(SubsidyNight::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyNight> subsidyNightList = subsidyNightService.list(subsidyNightWrap);
        Map<String, SubsidyNight> itemMemberUidNightSubsidy = null;
        if(!CollectionUtils.isEmpty(subsidyNightList)){
            itemMemberUidNightSubsidy  = subsidyNightList.stream().collect(Collectors.toMap(SubsidyNight::getUid, o->o));
        }

        //更新驻外补助
        LambdaQueryWrapper<SubsidyOut> subsidyOutWrap = new LambdaQueryWrapper();
        subsidyOutWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), SubsidyOut::getUid, itemUidItemMemberUidDate)
                .eq(SubsidyOut::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyOut> subsidyList = subsidyOutService.list(subsidyOutWrap);
        Map<String, SubsidyOut> itemMemberUidOutSubsidy = null;
        if(!CollectionUtils.isEmpty(subsidyList)){
            itemMemberUidOutSubsidy  = subsidyList.stream().collect(Collectors.toMap(SubsidyOut::getUid, o->o));
        }

        //更新加班补助
        LambdaQueryWrapper<SubsidyOvertime> subsidyOvertimeWrap = new LambdaQueryWrapper();
        subsidyOvertimeWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), SubsidyOvertime::getUid, itemUidItemMemberUidDate)
                .eq(SubsidyOvertime::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyOvertime> subsidyOvertimeList = subsidyOvertimeService.list(subsidyOvertimeWrap);
        Map<String, SubsidyOvertime> itemMemberUidOvertimeSubsidy = null;
        if(!CollectionUtils.isEmpty(subsidyOvertimeList)){
            itemMemberUidOvertimeSubsidy  = subsidyOvertimeList.stream().collect(Collectors.toMap(SubsidyOvertime::getUid, o->o));
        }

        List<TotalSalary> addCollect = new ArrayList<>();
        if(!CollectionUtils.isEmpty(addItemMember)){
            for(ItemMember itemMember: addItemMember) {
                Item item = itemUidItem.get(itemMember.getItemUid());
                String itemMemberUid = itemMember.getMemberUid();
                Member member = memberUidMember.get(itemMemberUid);
                String uid = item.getUid() + member.getUid() + DateUtil.getYM();
                TotalSalary totalSalary = new TotalSalary();
                totalSalary.setId(null)
                        .setUid(uid)
                        .setItemUid(item.getUid())
                        .setItemName(item.getItemName())
                        .setItemMemberUid(itemMember.getMemberUid())
                        .setRoom(member.getRoom())
                        .setDepartment(member.getDepartment())
                        .setNumber(member.getNumber())
                        .setName(member.getName())
                        .setDeclareGrant(itemMemberUidItemSalary!=null ? itemMemberUidItemSalary.get(uid).getDeclareGrant(): totalSalary.getDeclareGrant())
                        .setPerformanceTotal(itemMemberUidPerformanceSalary!=null ? itemMemberUidPerformanceSalary.get(uid).getPerformanceSalary(): totalSalary.getPerformanceTotal())
                        .setDesignTotal(itemMemberUidDesignSalary != null ? itemMemberUidDesignSalary.get(uid).getSubtotal() : totalSalary.getDesignTotal())
                        .setTenderTotal(itemMemberUidBidSalary!=null ? itemMemberUidBidSalary.get(uid).getBidFee() : totalSalary.getTenderTotal())
                        .setScientificTotal(itemMemberUidScientificSalary !=null ? itemMemberUidScientificSalary.get(uid).getSubtotal(): totalSalary.getScientificTotal())
                        .setOutSubsidy(itemMemberUidOutSubsidy !=null ? itemMemberUidOutSubsidy.get(uid).getOutSubsidy(): totalSalary.getOutSubsidy())
                        .setNightSubsidy(itemMemberUidNightSubsidy!=null ? itemMemberUidNightSubsidy.get(uid).getNightSubsidy() :totalSalary.getNightSubsidy())
                        .setOvertimeSubsidy(itemMemberUidOvertimeSubsidy!=null ? itemMemberUidOvertimeSubsidy.get(uid).getOvertimeSubsidy() : totalSalary.getOvertimeSubsidy())
                        .setHeatingSubsidy(itemMemberUidHeatingSubsidy!=null ? itemMemberUidHeatingSubsidy.get(uid).getHeatingSubsidy(): totalSalary.getHeatingSubsidy())
                        .setDeclareTime(new Date())
                        .setCreatedBy(currentNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedBy(currentNumber)
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                addCollect.add(totalSalary);
            }
            boolean b = totalSalaryService.saveBatch(addCollect);
            if (!b) {
                throw new BusinessException("工资汇总添加失败!");
            }
        }
        if(!CollectionUtils.isEmpty(updateItemMember)){
            List<TotalSalary> updateCollect = new ArrayList<>();
            for(TotalSalary oldTotalSalary : updateItemMember){
                Item item = itemUidItem.get(oldTotalSalary.getItemUid());
                Member member = memberUidMember.get(oldTotalSalary.getItemMemberUid());
                String uid = oldTotalSalary.getUid();
                oldTotalSalary.setItemName(item.getItemName())
                        .setRoom(member.getRoom())
                        .setDepartment(member.getDepartment())
                        .setNumber(member.getNumber())
                        .setName(member.getName())
                        .setDeclareGrant(itemMemberUidItemSalary!=null ? itemMemberUidItemSalary.get(uid).getDeclareGrant(): oldTotalSalary.getDeclareGrant())
                        .setPerformanceTotal(itemMemberUidPerformanceSalary!=null ? itemMemberUidPerformanceSalary.get(uid).getPerformanceSalary(): oldTotalSalary.getPerformanceTotal())
                        .setDesignTotal(itemMemberUidDesignSalary != null ? itemMemberUidDesignSalary.get(uid).getSubtotal() : oldTotalSalary.getDesignTotal())
                        .setTenderTotal(itemMemberUidBidSalary!=null ? itemMemberUidBidSalary.get(uid).getBidFee() : oldTotalSalary.getTenderTotal())
                        .setScientificTotal(itemMemberUidScientificSalary !=null ? itemMemberUidScientificSalary.get(uid).getSubtotal(): oldTotalSalary.getScientificTotal())
                        .setOutSubsidy(itemMemberUidOutSubsidy !=null ? itemMemberUidOutSubsidy.get(uid).getOutSubsidy(): oldTotalSalary.getOutSubsidy())
                        .setNightSubsidy(itemMemberUidNightSubsidy!=null ? itemMemberUidNightSubsidy.get(uid).getNightSubsidy() :oldTotalSalary.getNightSubsidy())
                        .setOvertimeSubsidy(itemMemberUidOvertimeSubsidy!=null ? itemMemberUidOvertimeSubsidy.get(uid).getOvertimeSubsidy() : oldTotalSalary.getOvertimeSubsidy())
                        .setHeatingSubsidy(itemMemberUidHeatingSubsidy!=null ? itemMemberUidHeatingSubsidy.get(uid).getHeatingSubsidy(): oldTotalSalary.getHeatingSubsidy())
                        .setDeclareTime(new Date())
                        .setUpdatedBy(currentNumber)
                        .setUpdatedTime(new Date());
                updateCollect.add(oldTotalSalary);
            }
            boolean b = totalSalaryService.updateBatchById(updateCollect);
            if (!b) {
                throw new BusinessException("项目人员信息更新失败!");
            }
        }
    }

    @Override
    public void flushMonthTotalSalaryItem(){
        User currentUser = UserRequest.getCurrentUser();
        flushMonthTotalSalaryItem(currentUser.getNumber());
    }

    @Override
    public void flushMonthTotalSalaryItem(String currentNumber) {
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.ne(Item::getItemStage, ItemStageEnum.STAGE_FINISH.getCode())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(queryWrapper);
        if(CollectionUtil.isEmpty(itemList)){
            return;
        }
        Map<String, Item> itemUidItem = itemList.stream().collect(Collectors.toMap(Item::getUid, O -> O));

        List<String> itemUids = itemList.stream().map(Item::getUid).collect(Collectors.toList());
        LambdaQueryWrapper<ItemMember> itemMemberWrap = new LambdaQueryWrapper();
        itemMemberWrap.in(CollectionUtil.isNotEmpty(itemUids), ItemMember::getItemUid, itemUids)
                .eq(ItemMember::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemMember> itemMemberList = itemMemberService.list(itemMemberWrap);
        if(CollectionUtil.isEmpty(itemMemberList)){
            return;
        }

        List<String> itemMemberUids = itemMemberList.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        LambdaQueryWrapper<Member> memberWrap = new LambdaQueryWrapper();
        memberWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), Member::getUid, itemMemberUids)
                .eq(Member::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Member> memberList = memberService.list(memberWrap);
        if(CollectionUtils.isEmpty(memberList)){
            return;
        }
        Map<String, Member> memberUidMember = memberList.stream().collect(Collectors.toMap(Member::getUid, o -> o));

        List<String> itemUidItemMemberUidDate = new ArrayList<>();
        for(ItemMember itemMember : itemMemberList){
            String key = itemMember.getItemUid() + itemMember.getMemberUid() + DateUtil.getYM();
            itemUidItemMemberUidDate.add(key);
        }

        LambdaQueryWrapper<TotalSalary> totalSalaryWrap = new LambdaQueryWrapper();
        totalSalaryWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), TotalSalary::getUid, itemUidItemMemberUidDate)
                .likeRight( TotalSalary:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrap);
        Map<String, TotalSalary> uidTotalSalary = totalSalaryList.stream().collect(Collectors.toMap(TotalSalary::getUid, o -> o));


        LambdaQueryWrapper<TotalSalaryItem> totalSalaryItemWrap = new LambdaQueryWrapper();
        totalSalaryItemWrap.in(CollectionUtil.isNotEmpty(itemUidItemMemberUidDate), TotalSalaryItem::getUid, itemUidItemMemberUidDate)
                .likeRight( TotalSalaryItem:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalaryItem::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalaryItem> totalSalaryItemList = totalSalaryItemService.list(totalSalaryItemWrap);

        List<TotalSalary> addTotalSalary = new ArrayList<>();
        List<TotalSalaryItem> updateTotalSalaryItem = new ArrayList<>();
        Map<String, TotalSalaryItem> uidTotalSalaryItem = totalSalaryItemList.stream().collect(Collectors.toMap(TotalSalaryItem::getUid, o -> o));
        for(Map.Entry<String, TotalSalary> entry : uidTotalSalary.entrySet()){
            if(uidTotalSalaryItem.containsKey(entry.getKey())){
                updateTotalSalaryItem.add(uidTotalSalaryItem.get(entry.getKey()));
            }else{
                addTotalSalary.add(entry.getValue());
            }
        }
        if(CollectionUtils.isEmpty(addTotalSalary) && CollectionUtils.isEmpty(updateTotalSalaryItem)){
            return;
        }



        LambdaQueryWrapper<ItemSalary> itemSalaryWrap = new LambdaQueryWrapper();
        itemSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemSalary::getItemMemberUid, itemMemberUids)
                .likeRight( ItemSalary:: getDeclareTime, DateUtil.getYM())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaryWrapList = itemSalaryService.list(itemSalaryWrap);
        Map<String, ItemSalary> itemMemberUidItemSalary = null;
        if(!CollectionUtils.isEmpty(itemSalaryWrapList)){
            itemMemberUidItemSalary = itemSalaryWrapList.stream().collect(Collectors.toMap(ItemSalary::getUid, o->o));
        }

        if(!CollectionUtils.isEmpty(addTotalSalary)){
            List<TotalSalaryItem> collect = new ArrayList<>();
            for(TotalSalary var : addTotalSalary){
                TotalSalaryItem totalSalaryItem = doTotalSalaryItem(var,currentNumber, itemUidItem, memberUidMember,itemMemberUidItemSalary);
                totalSalaryItem.setCreatedBy(currentNumber)
                        .setCreatedTime(new Date());
                collect.add(totalSalaryItem);
            }
            boolean b = totalSalaryItemService.saveBatch(collect);
            if (!b) {
                throw new BusinessException("项目汇总工资添加失败!");
            }
        }

        if(!CollectionUtils.isEmpty(updateTotalSalaryItem)){
            List<TotalSalaryItem> collect = new ArrayList<>();
            for(TotalSalaryItem var : updateTotalSalaryItem) {
                TotalSalary totalSalary = uidTotalSalary.get(var.getUid());
                TotalSalaryItem totalSalaryItem = doTotalSalaryItem(totalSalary,currentNumber, itemUidItem, memberUidMember,itemMemberUidItemSalary);
                totalSalaryItem.setId(var.getId());
                collect.add(totalSalaryItem);
            }
            boolean b = totalSalaryItemService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目汇总工资更新失败!");
            }
        }
    }

    private TotalSalaryItem doTotalSalaryItem(TotalSalary var,String currentNumber,Map<String, Item> itemUidItem
            ,Map<String, Member> memberUidMember,Map<String, ItemSalary> itemMemberUidItemSalary){
        Item item = itemUidItem.get(var.getItemUid());
        Member member = memberUidMember.get(var.getItemMemberUid());
        ItemSalary itemSalary = itemMemberUidItemSalary != null ? itemMemberUidItemSalary.get(var.getItemMemberUid()) : null;
        String uid = itemUidItem.get(var.getItemUid()).getUid() + memberUidMember.get(var.getItemMemberUid()).getUid() + DateUtil.getYM();
        TotalSalaryItem totalSalaryItem = TotalSalaryItem.builder()
                .uid(uid)
                .itemUid(var.getUid())
                .itemName(item.getItemName())
                .itemMemberUid(var.getItemMemberUid())
                .number(member.getNumber())
                .name(member.getName())
                .subItemNumber(item.getSubItemNumber())
                .virtualSubItemNumbe(item.getVirtualSubItemNumber())
                .itemNumber(item.getItemNumber())
                .agreementMoney(item.getAgreementMoney())
                .itemManager(item.getItemManager())
                .bidDirector(item.getBidDirector())
                .designManager(item.getDesignManager())
                .scientificManager(item.getScientificManager())
                .postSalaryStandard(var.getPerformanceTotal())
                .declareFactor(itemSalary!=null ? itemSalary.getDeclareFactor() : null)
                .checkPlanSalary(itemSalary!=null ? new BigDecimal(itemSalary.getCheckPlanSalary()) : null)
                .checkSalary(itemSalary!=null ? new BigDecimal(itemSalary.getCheckSalary()) : null)
                .declareGrant(itemSalary!=null ? itemSalary.getDeclareGrant() : null)
                .performanceSalary(var.getPerformanceTotal())
                .designSalary(var.getDesignTotal())
                .bidSalary(var.getTenderTotal())
                .subsidyCoefficient(var.getSubsidyCoefficient())
                .planSubsidy(var.getPlanSubsidy())
                .checkSubsidy(var.getCheckSubsidy())
                .realitySubsidy(var.getRealitySubsidy())
                .declareTime(var.getDeclareTime())
                .totalSalary(var.getPlanSalary()!=null ? new BigDecimal(var.getPlanSalary()) : null)
                .mark(var.getRemarks())
                .updatedBy(currentNumber)
                .updatedTime(new Date())
                .isDeleted(GlobalConstant.GLOBAL_STR_ZERO)
                .build();
        return totalSalaryItem;
    }

    @Override
    public void flushMonthTotalSalaryRoom(){
        User currentUser = UserRequest.getCurrentUser();
        flushMonthTotalSalaryRoom(currentUser.getNumber());
    }

    @Override
    public void flushMonthTotalSalaryRoom(String currentNumber) {
        //项目人员的添加和更新
        LambdaQueryWrapper<Item> itemQueryWrapper = new LambdaQueryWrapper();
        itemQueryWrapper.ne(Item::getItemStage, ItemStageEnum.STAGE_FINISH.getCode())
                .eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(itemQueryWrapper);
        if(CollectionUtil.isEmpty(itemList)){
            return;
        }
        Map<String, Item> itemUidItem = itemList.stream().collect(Collectors.toMap(Item::getUid, o -> o));

        LambdaQueryWrapper<TotalSalary> totalSalaryWrapper = new LambdaQueryWrapper();
        totalSalaryWrapper.likeRight(TotalSalary:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrapper);
        Set<String> TotalSalaryItemUids = totalSalaryList.stream().map(TotalSalary::getItemUid).collect(Collectors.toSet());
        Map<String, TotalSalaryRoom> itemUidTotalSalaryTotalSalaryRoom = new HashMap<>();
        for(TotalSalary totalSalary : totalSalaryList){
            String itemUid = totalSalary.getItemUid();
            String planSalary = totalSalary.getPlanSalary();
            Date declareTime = totalSalary.getDeclareTime();
            if(itemUidTotalSalaryTotalSalaryRoom.containsKey(itemUid)){
                TotalSalaryRoom totalSalaryRoom = itemUidTotalSalaryTotalSalaryRoom.get(itemUid);
                BigDecimal total = totalSalaryRoom.getTotalSalary().add(planSalary !=null ? new BigDecimal(planSalary) : new BigDecimal(0));
                totalSalaryRoom.setTotalSalary(total)
                        .setDeclareTime(declareTime);
            }else{
                TotalSalaryRoom totalSalaryRoom = TotalSalaryRoom.builder()
                        .totalSalary(totalSalary.getPlanSalary()!=null ? new BigDecimal(totalSalary.getPlanSalary()) :  new BigDecimal(0))
                        .declareTime(totalSalary.getDeclareTime())
                        .build();
                itemUidTotalSalaryTotalSalaryRoom.put(itemUid,totalSalaryRoom);
            }
        }

        LambdaQueryWrapper<TotalSalaryRoom> wrapper = new LambdaQueryWrapper();
        wrapper.likeRight(TotalSalaryRoom:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalaryRoom> totalSalaryRoomList = totalSalaryRoomService.list(wrapper);
        Map<String, TotalSalaryRoom> itemUidTotalSalaryRoom = totalSalaryRoomList.stream().collect(Collectors.toMap(TotalSalaryRoom::getItemUid, o -> o));

        List<String> addTotalSalaryRoom = new ArrayList<>();
        List<TotalSalaryRoom> updateTotalSalaryRoom = new ArrayList<>();
        for(String itemUid : TotalSalaryItemUids){
            if(itemUidTotalSalaryRoom.containsKey(itemUid)){
                updateTotalSalaryRoom.add(itemUidTotalSalaryRoom.get(itemUid));
            }else{
                addTotalSalaryRoom.add(itemUid);
            }
        }
        if(CollectionUtils.isEmpty(addTotalSalaryRoom) && CollectionUtils.isEmpty(updateTotalSalaryRoom)){
            return;
        }

        if(!CollectionUtils.isEmpty(addTotalSalaryRoom)){
            List<TotalSalaryRoom> collect = addTotalSalaryRoom.stream().map(itemUid -> {
                TotalSalaryRoom totalSalaryRoom = doTotalSalaryRoom(itemUid, itemUidItem, itemUidTotalSalaryTotalSalaryRoom, currentNumber);
                totalSalaryRoom.setCreatedBy(currentNumber)
                        .setCreatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                return totalSalaryRoom;
            }).collect(Collectors.toList());

            boolean b = totalSalaryRoomService.saveBatch(collect);
            if (!b) {
                throw new BusinessException("项目工资添加失败!");
            }
        }

        if(!CollectionUtils.isEmpty(updateTotalSalaryRoom)){
            List<TotalSalaryRoom> collect = updateTotalSalaryRoom.stream().map(oldTotalSalaryRoom -> {
                TotalSalaryRoom totalSalaryRoom = doTotalSalaryRoom(oldTotalSalaryRoom.getItemUid(), itemUidItem, itemUidTotalSalaryTotalSalaryRoom, currentNumber);
                totalSalaryRoom.setId(oldTotalSalaryRoom.getId());
                return totalSalaryRoom;
            }).collect(Collectors.toList());

            boolean b = totalSalaryRoomService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目工资更新失败!");
            }
        }
    }

    private TotalSalaryRoom doTotalSalaryRoom(String itemUid, Map<String, Item> itemUidItem
            ,Map<String, TotalSalaryRoom> itemUidTotalSalaryTotalSalaryRoom, String currentNumber){
        Item item = itemUidItem.get(itemUid);
        TotalSalaryRoom totalSalaryTotalSalaryRoom = itemUidTotalSalaryTotalSalaryRoom.get(itemUid);
        String uid = item.getUid() + DateUtil.getYM();
        TotalSalaryRoom totalSalaryRoom =TotalSalaryRoom.builder()
                .uid(uid)
                .itemUid(itemUid)
                .room(item.getRoom())
                .subItemNumber(item.getSubItemNumber())
                .virtualSubItemNumbe(item.getVirtualSubItemNumber())
                .itemNumber(item.getItemNumber())
                .itemName(item.getItemName())
                .itemStage(String.valueOf(item.getItemStage()))
                .itemManager(item.getItemManager())
                .declareTime(totalSalaryTotalSalaryRoom.getDeclareTime())
                .totalSalary(totalSalaryTotalSalaryRoom.getTotalSalary())
                .updatedBy(currentNumber)
                .updatedTime(new Date())
                .build();
        return totalSalaryRoom;
    }
}

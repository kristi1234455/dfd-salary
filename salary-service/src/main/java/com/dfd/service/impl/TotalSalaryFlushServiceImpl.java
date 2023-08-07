package com.dfd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.CollectorUtil;
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
        User currentUser = UserRequest.getCurrentUser();

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
        List<String> itemMemberUids = itemMemberList.stream().map(ItemMember::getMemberUid).collect(Collectors.toList());
        LambdaQueryWrapper<ItemSalary> itemSalaryWrap = new LambdaQueryWrapper();
        itemSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemSalary::getItemMemberUid, itemMemberUids)
                .likeRight( ItemSalary:: getDeclareTime, DateUtil.getYM())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaryWrapList = itemSalaryService.list(itemSalaryWrap);
        Map<String, BigDecimal> itemMemberUidItemSalary = null;
        if(!CollectionUtils.isEmpty(itemSalaryWrapList)){
            itemMemberUidItemSalary = itemSalaryWrapList.stream()
                    .collect(Collectors.toMap(ItemSalary::getUid, ItemSalary::getDeclareGrant));
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
                    .collect(Collectors.toMap(PerformanceSalary::getUid, PerformanceSalary::getPerformanceSalary));
        }

        //更新设计工资
        LambdaQueryWrapper<DesignSalary> designSalaryWrap = new LambdaQueryWrapper();
        designSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), DesignSalary::getItemMemberUid, itemMemberUids)
                .likeRight( DesignSalary:: getDeclareTime, DateUtil.getYM())
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<DesignSalary> designSalaryList = designSalaryService.list(designSalaryWrap);
        Map<String, BigDecimal> itemMemberUidDesignSalary = null;
        if(!CollectionUtils.isEmpty(designSalaryList)){
            itemMemberUidDesignSalary = designSalaryList.stream().collect(Collectors.toMap(DesignSalary::getUid, DesignSalary::getSubtotal));
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
                    .collect(Collectors.toMap(BidSalary::getUid, BidSalary::getBidFee));
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
                    .collect(Collectors.toMap(ScientificSalary::getUid, ScientificSalary::getSubtotal));
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
            itemMemberUidOutSubsidy  = subsidyList.stream().collect(Collectors.toMap(Subsidy::getUid, Subsidy::getOutSubsidy));
            itemMemberUidNightSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getUid, Subsidy::getNightSubsidy));
            itemMemberUidOvertimeSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getUid, Subsidy::getOvertimeSubsidy));
            itemMemberUidHeatingSubsidy = subsidyList.stream().collect(Collectors.toMap(Subsidy::getUid, Subsidy::getHeatingSubsidy));
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
                        .setDeclareGrant(itemMemberUidItemSalary!=null ? itemMemberUidItemSalary.get(uid): totalSalary.getDeclareGrant())
                        .setPerformanceTotal(itemMemberUidPerformanceSalary!=null ? itemMemberUidPerformanceSalary.get(uid): totalSalary.getPerformanceTotal())
                        .setDesignTotal(itemMemberUidDesignSalary != null ? itemMemberUidDesignSalary.get(uid) : totalSalary.getDesignTotal())
                        .setTenderTotal(itemMemberUidBidSalary!=null ? itemMemberUidBidSalary.get(uid) : totalSalary.getTenderTotal())
                        .setScientificTotal(itemMemberUidScientificSalary !=null ? itemMemberUidScientificSalary.get(uid): totalSalary.getScientificTotal())
                        .setOutSubsidy(itemMemberUidOutSubsidy !=null ? itemMemberUidOutSubsidy.get(uid) : totalSalary.getOutSubsidy())
                        .setNightSubsidy(itemMemberUidNightSubsidy!=null ? itemMemberUidNightSubsidy.get(uid) :totalSalary.getNightSubsidy())
                        .setOvertimeSubsidy(itemMemberUidOvertimeSubsidy!=null ? itemMemberUidOvertimeSubsidy.get(uid) : totalSalary.getOvertimeSubsidy())
                        .setHeatingSubsidy(itemMemberUidHeatingSubsidy!=null ? itemMemberUidHeatingSubsidy.get(uid): totalSalary.getHeatingSubsidy())
                        .setDeclareTime(new Date())
                        .setCreatedBy(currentUser.getNumber())
                        .setCreatedTime(new Date())
                        .setUpdatedBy(currentUser.getNumber())
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
                        .setDeclareGrant(itemMemberUidItemSalary!=null ? itemMemberUidItemSalary.get(uid): oldTotalSalary.getDeclareGrant())
                        .setPerformanceTotal(itemMemberUidPerformanceSalary!=null ? itemMemberUidPerformanceSalary.get(uid): oldTotalSalary.getPerformanceTotal())
                        .setDesignTotal(itemMemberUidDesignSalary != null ? itemMemberUidDesignSalary.get(uid) : oldTotalSalary.getDesignTotal())
                        .setTenderTotal(itemMemberUidBidSalary!=null ? itemMemberUidBidSalary.get(uid) : oldTotalSalary.getTenderTotal())
                        .setScientificTotal(itemMemberUidScientificSalary !=null ? itemMemberUidScientificSalary.get(uid): oldTotalSalary.getScientificTotal())
                        .setOutSubsidy(itemMemberUidOutSubsidy !=null ? itemMemberUidOutSubsidy.get(uid) : oldTotalSalary.getOutSubsidy())
                        .setNightSubsidy(itemMemberUidNightSubsidy!=null ? itemMemberUidNightSubsidy.get(uid) :oldTotalSalary.getNightSubsidy())
                        .setOvertimeSubsidy(itemMemberUidOvertimeSubsidy!=null ? itemMemberUidOvertimeSubsidy.get(uid) : oldTotalSalary.getOvertimeSubsidy())
                        .setHeatingSubsidy(itemMemberUidHeatingSubsidy!=null ? itemMemberUidHeatingSubsidy.get(uid): oldTotalSalary.getHeatingSubsidy())
                        .setDeclareTime(new Date())
                        .setUpdatedBy(currentUser.getNumber())
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

        LambdaQueryWrapper<TotalSalary> totalSalaryWrap = new LambdaQueryWrapper();
        totalSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), TotalSalary::getItemMemberUid, itemMemberUids)
                .likeRight( TotalSalary:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrap);
        Map<String, TotalSalary> uidTotalSalary = totalSalaryList.stream().collect(Collectors.toMap(TotalSalary::getUid, o -> o));

        LambdaQueryWrapper<TotalSalaryItem> totalSalaryItemWrap = new LambdaQueryWrapper();
        totalSalaryItemWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), TotalSalaryItem::getItemMemberUid, itemMemberUids)
                .likeRight( TotalSalaryItem:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalaryItem::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<TotalSalaryItem> totalSalaryItemList = totalSalaryItemService.list(totalSalaryItemWrap);

        List<TotalSalary> addTotalSalary = totalSalaryList.stream()
                .filter(obj1 -> totalSalaryItemList.stream().noneMatch(obj2 -> obj1.getUid().equals(obj2.getUid())))
                .collect(Collectors.toList());
        List<TotalSalaryItem> updateTotalSalaryItem = totalSalaryItemList.stream()
                .filter(obj1 -> addTotalSalary.stream().noneMatch(obj2 -> obj1.getUid().equals(obj2.getUid())))
                .collect(Collectors.toList());

        User currentUser = UserRequest.getCurrentUser();

        LambdaQueryWrapper<ItemSalary> itemSalaryWrap = new LambdaQueryWrapper();
        itemSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), ItemSalary::getItemMemberUid, itemMemberUids)
                .likeRight( ItemSalary:: getDeclareTime, DateUtil.getYM())
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaryWrapList = itemSalaryService.list(itemSalaryWrap);
        Map<String, ItemSalary> itemMemberUidItemSalary = null;
        if(!CollectionUtils.isEmpty(itemSalaryWrapList)){
            itemMemberUidItemSalary = itemSalaryWrapList.stream().collect(Collectors.toMap(ItemSalary::getItemMemberUid, o->o));
        }

        if(!CollectionUtils.isEmpty(addTotalSalary)){
            List<TotalSalaryItem> collect = new ArrayList<>();
            for(TotalSalary var : addTotalSalary){
                TotalSalaryItem totalSalaryItem = doTotalSalaryItem(var,currentUser, itemUidItem, memberUidMember,itemMemberUidItemSalary);
                totalSalaryItem.setUid(itemUidItem.get(var.getItemUid()).getUid()+memberUidMember.get(var.getItemMemberUid()).getUid()+DateUtil.getYM())
                        .setCreatedBy(currentUser.getNumber())
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
                TotalSalaryItem totalSalaryItem = doTotalSalaryItem(totalSalary,currentUser, itemUidItem, memberUidMember,itemMemberUidItemSalary);
                totalSalaryItem.setId(var.getId());
                collect.add(totalSalaryItem);
            }
            boolean b = totalSalaryItemService.updateBatchById(collect);
            if (!b) {
                throw new BusinessException("项目汇总工资更新失败!");
            }
        }

    }

    private TotalSalaryItem doTotalSalaryItem(TotalSalary var,User currentUser,Map<String, Item> itemUidItem
            ,Map<String, Member> memberUidMember,Map<String, ItemSalary> itemMemberUidItemSalary){
        Item item = itemUidItem.get(var.getItemUid());
        Member member = memberUidMember.get(var.getItemMemberUid());
        ItemSalary itemSalary = itemMemberUidItemSalary != null ? itemMemberUidItemSalary.get(var.getItemMemberUid()) : null;
        TotalSalaryItem totalSalaryItem = TotalSalaryItem.builder()
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
                .declareFactor(itemSalary.getDeclareFactor())
                .checkPlanSalary(itemSalary.getCheckPlanSalary()!=null ? new BigDecimal(itemSalary.getCheckPlanSalary()) : null)
                .checkSalary(itemSalary.getCheckSalary()!=null ? new BigDecimal(itemSalary.getCheckSalary()) : null)
                .declareGrant(itemSalary.getDeclareGrant())
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
                .updatedBy(currentUser.getNumber())
                .updatedTime(new Date())
                .isDeleted(GlobalConstant.GLOBAL_STR_ZERO)
                .build();
        return totalSalaryItem;
    }

    @Override
    public void flushMonthTotalSalaryRoom(){
        //todo：total-salary到total-salary-room
        LambdaQueryWrapper<Item> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Item::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Item> itemList = itemService.list(queryWrapper);

//        LambdaQueryWrapper<TotalSalary> totalSalaryWrap = new LambdaQueryWrapper();
//        totalSalaryWrap.in(CollectionUtil.isNotEmpty(itemMemberUids), TotalSalary::getItemMemberUid, itemMemberUids)
//                .likeRight( TotalSalary:: getDeclareTime, DateUtil.getYM())
//                .eq(TotalSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
//        List<TotalSalary> totalSalaryList = totalSalaryService.list(totalSalaryWrap);

        LambdaQueryWrapper<TotalSalaryRoom> wrapper = new LambdaQueryWrapper();
        wrapper.likeRight(TotalSalaryRoom:: getDeclareTime, DateUtil.getYM())
                .eq(TotalSalaryRoom::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
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

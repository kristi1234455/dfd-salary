package com.dfd.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dfd.constant.GlobalConstant;
import com.dfd.dto.ItemMemberAddListDTO;
import com.dfd.dto.ItemMemberDTO;
import com.dfd.entity.*;
import com.dfd.enums.ItemStageEnum;
import com.dfd.service.*;
import com.dfd.service.util.UserRequest;
import com.dfd.utils.BusinessException;
import com.dfd.utils.DateUtil;
import com.dfd.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FlushItemMemberServiceImpl implements FlushItemMemberService {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ItemSalaryService itemSalaryService;

    @Autowired
    private PerformanceSalaryService performanceSalaryService;

    @Autowired
    private SpecialSalaryService specialSalaryService;

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

    @Override
    public void flushToSalaryCategory(ItemMemberAddListDTO itemMemberAddListDTO) {
        String itemUid = itemMemberAddListDTO.getItemUid();
        List<ItemMemberDTO> itemMemberDTOS = itemMemberAddListDTO.getItemMemberDTOS();
        String currentUserNumber = itemMemberAddListDTO.getCurrentUserNumber();

        //刷新到考勤
        LambdaQueryWrapper<Attendance> attendanceLambdaQueryWrapper = new LambdaQueryWrapper();
        attendanceLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), Attendance:: getItemUid, itemUid)
                .eq(Attendance::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<Attendance> attdances = attendanceService.list(attendanceLambdaQueryWrapper);
        List<String> attdanceMembers = attdances.stream().map(Attendance::getUid).collect(Collectors.toList());
        List<Attendance> attdancesResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            String uid = itemUid + itemMemberDTO.getMemberUid() + DateUtil.getY() + DateUtil.getM() + DateUtil.getD();
            if(!attdanceMembers.contains(uid)){
                Attendance var = new Attendance();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setYear(Integer.parseInt(DateUtil.getY()))
                        .setMonth(Integer.parseInt(DateUtil.getM()))
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                attdancesResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(attdancesResult)){
            boolean attendanceBoolean = attendanceService.saveBatch(attdancesResult);
            if (!attendanceBoolean) {
                throw new BusinessException("项目人员添加到考勤保存失败");
            }
        }


        //刷新到项目工资:前期和后期
        List<ItemSalary> itemSalaryResult = new ArrayList<>();
        LambdaQueryWrapper<ItemSalary> itemSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        itemSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), ItemSalary:: getItemUid, itemUid)
                .eq(ItemSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ItemSalary> itemSalaries = itemSalaryService.list(itemSalaryLambdaQueryWrapper);
        List<String> itemSalaryMembers = itemSalaries.stream().map(ItemSalary::getItemMemberUid).collect(Collectors.toList());

        for(ItemStageEnum value : ItemStageEnum.values()){
            String itemStage = value.getCode();
            itemMemberDTOS.stream().forEach(itemMemberDTO ->{
                String memberUid = itemMemberDTO.getMemberUid();
                if(!itemSalaryMembers.contains(memberUid)){
                    ItemSalary var = new ItemSalary();
                    String uid = itemUid + memberUid + DateUtil.getYM() + itemStage;
                    var.setUid(uid)
                            .setItemUid(itemUid)
                            .setItemMemberUid(memberUid)
                            .setItemStage(itemStage)
                            .setDeclareTime(DateUtil.getYM())
                            .setCreatedBy(currentUserNumber)
                            .setUpdatedBy(currentUserNumber)
                            .setCreatedTime(new Date())
                            .setUpdatedTime(new Date())
                            .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                    itemSalaryResult.add(var);
                }
            });
        }
        if(CollectionUtil.isNotEmpty(itemSalaryResult)){
            boolean itemSalaryBoolean = itemSalaryService.saveBatch(itemSalaryResult);
            if (!itemSalaryBoolean) {
                throw new BusinessException("项目人员添加到项目工资保存失败");
            }
        }

        //刷新到投标工资
        LambdaQueryWrapper<BidSalary> bidSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        bidSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), BidSalary:: getItemUid, itemUid)
                .eq(BidSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<BidSalary> bidSalaries = bidSalaryService.list(bidSalaryLambdaQueryWrapper);
        List<String> bidSalaryMembers = bidSalaries.stream().map(BidSalary::getItemMemberUid).collect(Collectors.toList());
        List<BidSalary> bidSalaryResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!bidSalaryMembers.contains(memberUid)){
                BidSalary var = new BidSalary();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                bidSalaryResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(bidSalaryResult)){
            boolean bidSalaryBoolean = bidSalaryService.saveBatch(bidSalaryResult);
            if (!bidSalaryBoolean) {
                throw new BusinessException("项目人员添加到投标保存失败");
            }
        }


        //刷新到设计工资
        LambdaQueryWrapper<DesignSalary> designSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        designSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), DesignSalary:: getItemUid, itemUid)
                .eq(DesignSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<DesignSalary> designSalaries = designSalaryService.list(designSalaryLambdaQueryWrapper);
        List<String> designSalaryMembers = designSalaries.stream().map(DesignSalary::getItemMemberUid).collect(Collectors.toList());
        List<DesignSalary> designSalaryResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!designSalaryMembers.contains(memberUid)){
                String uid = itemUid + memberUid + DateUtil.getYM();
                DesignSalary var = new DesignSalary();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                designSalaryResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(designSalaryResult)){
            boolean designSalaryBoolean = designSalaryService.saveBatch(designSalaryResult);
            if (!designSalaryBoolean) {
                throw new BusinessException("项目人员添加到设计保存失败");
            }
        }


        //刷新到绩效工资
        LambdaQueryWrapper<PerformanceSalary> performanceSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        performanceSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), PerformanceSalary:: getItemUid, itemUid)
                .eq(PerformanceSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<PerformanceSalary> performanceSalaries = performanceSalaryService.list(performanceSalaryLambdaQueryWrapper);
        List<String> performanceSalaryMembers = performanceSalaries.stream().map(PerformanceSalary::getItemMemberUid).collect(Collectors.toList());
        List<PerformanceSalary> performanceSalaryResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!performanceSalaryMembers.contains(memberUid)){
                PerformanceSalary var = new PerformanceSalary();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                performanceSalaryResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(performanceSalaryResult)){
            boolean performanceSalaryBoolean = performanceSalaryService.saveBatch(performanceSalaryResult);
            if (!performanceSalaryBoolean) {
                throw new BusinessException("项目人员添加到绩效保存失败");
            }
        }


        //刷新到专岗工资
        LambdaQueryWrapper<SpecialSalary> specialSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        specialSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), SpecialSalary:: getItemUid, itemUid)
                .eq(SpecialSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SpecialSalary> specialSalaries = specialSalaryService.list(specialSalaryLambdaQueryWrapper);
        List<String> specialSalaryMembers = specialSalaries.stream().map(SpecialSalary::getItemMemberUid).collect(Collectors.toList());
        List<SpecialSalary> specialSalaryResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!specialSalaryMembers.contains(memberUid)){
                SpecialSalary var = new SpecialSalary();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setSpecialDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                specialSalaryResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(specialSalaryResult)){
            boolean specialSalaryBoolean = specialSalaryService.saveBatch(specialSalaryResult);
            if (!specialSalaryBoolean) {
                throw new BusinessException("项目人员添加到专岗工资保存失败");
            }
        }

        //刷新到科研工资
        LambdaQueryWrapper<ScientificSalary> scientificSalaryLambdaQueryWrapper = new LambdaQueryWrapper();
        scientificSalaryLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), ScientificSalary:: getItemUid, itemUid)
                .eq(ScientificSalary::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<ScientificSalary> scientificSalaries = scientificSalaryService.list(scientificSalaryLambdaQueryWrapper);
        List<String> scientificSalaryMembers = scientificSalaries.stream().map(ScientificSalary::getItemMemberUid).collect(Collectors.toList());
        List<ScientificSalary> scientificSalaryResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!scientificSalaryMembers.contains(memberUid)){
                ScientificSalary var = new ScientificSalary();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                scientificSalaryResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(scientificSalaryResult)){
            boolean scientificSalaryBoolean = scientificSalaryService.saveBatch(scientificSalaryResult);
            if (!scientificSalaryBoolean) {
                throw new BusinessException("项目人员添加到科研保存失败");
            }
        }


        //刷新到高温工资
        LambdaQueryWrapper<SubsidyHeating> subsidyHeatingLambdaQueryWrapper = new LambdaQueryWrapper();
        subsidyHeatingLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), SubsidyHeating:: getItemUid, itemUid)
                .eq(SubsidyHeating::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyHeating> subsidyHeatings = subsidyHeatingService.list(subsidyHeatingLambdaQueryWrapper);
        List<String> subsidyHeatingMembers = subsidyHeatings.stream().map(SubsidyHeating::getItemMemberUid).collect(Collectors.toList());
        List<SubsidyHeating> subsidyHeatingResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!subsidyHeatingMembers.contains(memberUid)){
                SubsidyHeating var = new SubsidyHeating();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setHeatingDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                subsidyHeatingResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(subsidyHeatingResult)){
            boolean subsidyHeatingBoolean = subsidyHeatingService.saveBatch(subsidyHeatingResult);
            if (!subsidyHeatingBoolean) {
                throw new BusinessException("项目人员添加到高温保存失败");
            }
        }


        //刷新到夜班工资
        LambdaQueryWrapper<SubsidyNight> subsidyNightLambdaQueryWrapper = new LambdaQueryWrapper();
        subsidyNightLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), SubsidyNight:: getItemUid, itemUid)
                .eq(SubsidyNight::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyNight> subsidyNights = subsidyNightService.list(subsidyNightLambdaQueryWrapper);
        List<String> subsidyNightMembers = subsidyNights.stream().map(SubsidyNight::getItemMemberUid).collect(Collectors.toList());
        List<SubsidyNight> subsidyNightResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!subsidyNightMembers.contains(memberUid)){
                SubsidyNight var = new SubsidyNight();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setNightDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                subsidyNightResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(subsidyNightResult)){
            boolean subsidyNightBoolean = subsidyNightService.saveBatch(subsidyNightResult);
            if (!subsidyNightBoolean) {
                throw new BusinessException("项目人员添加到夜班保存失败");
            }
        }


        //刷新到驻外工资
        LambdaQueryWrapper<SubsidyOut> subsidyOutLambdaQueryWrapper = new LambdaQueryWrapper();
        subsidyOutLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), SubsidyOut:: getItemUid, itemUid)
                .eq(SubsidyOut::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyOut> subsidyOuts = subsidyOutService.list(subsidyOutLambdaQueryWrapper);
        List<String> subsidyOutMembers = subsidyOuts.stream().map(SubsidyOut::getItemMemberUid).collect(Collectors.toList());
        List<SubsidyOut> subsidyOutResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!subsidyOutMembers.contains(memberUid)){
                SubsidyOut var = new SubsidyOut();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setOutDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                subsidyOutResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(subsidyOutResult)){
            boolean subsidyOutBoolean = subsidyOutService.saveBatch(subsidyOutResult);
            if (!subsidyOutBoolean) {
                throw new BusinessException("项目人员添加到驻外保存失败");
            }
        }


        //刷新到加班工资
        LambdaQueryWrapper<SubsidyOvertime> subsidyOvertimeLambdaQueryWrapper = new LambdaQueryWrapper();
        subsidyOvertimeLambdaQueryWrapper.eq(StringUtils.isNotBlank(itemUid), SubsidyOvertime:: getItemUid, itemUid)
                .eq(SubsidyOvertime::getIsDeleted, GlobalConstant.GLOBAL_STR_ZERO);
        List<SubsidyOvertime> subsidyOvertimes = subsidyOvertimeService.list(subsidyOvertimeLambdaQueryWrapper);
        List<String> subsidyOvertimeMembers = subsidyOvertimes.stream().map(SubsidyOvertime::getItemMemberUid).collect(Collectors.toList());
        List<SubsidyOvertime> subsidyOvertimeResult = new ArrayList<>();
        itemMemberDTOS.stream().forEach(itemMemberDTO ->{
            String memberUid = itemMemberDTO.getMemberUid();
            if(!subsidyOvertimeMembers.contains(memberUid)){
                SubsidyOvertime var = new SubsidyOvertime();
                String uid = itemUid + memberUid + DateUtil.getYM();
                var.setUid(uid)
                        .setItemUid(itemUid)
                        .setItemMemberUid(memberUid)
                        .setOvertimeDeclareTime(DateUtil.getYM())
                        .setCreatedBy(currentUserNumber)
                        .setUpdatedBy(currentUserNumber)
                        .setCreatedTime(new Date())
                        .setUpdatedTime(new Date())
                        .setIsDeleted(GlobalConstant.GLOBAL_STR_ZERO);
                subsidyOvertimeResult.add(var);
            }
        });
        if(CollectionUtil.isNotEmpty(subsidyOvertimeResult)){
            boolean subsidyOvertimeBoolean = subsidyOvertimeService.saveBatch(subsidyOvertimeResult);
            if (!subsidyOvertimeBoolean) {
                throw new BusinessException("项目人员添加到加班保存失败");
            }
        }
    }
}

package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Subsidy {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Long id;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 驻外补助标准（元/天）
     */
    @ApiModelProperty(value = "驻外补助标准（元/天）", name = "outSubsidyStandard")
    private BigDecimal outSubsidyStandard;

    /**
     * 驻外天数
     */
    @ApiModelProperty(value = "驻外天数", name = "outDays")
    private Integer outDays;

    /**
     * 驻外补助合计（元）
     */
    @ApiModelProperty(value = "驻外补助合计（元）", name = "outSubsidy")
    private BigDecimal outSubsidy;

    /**
     * 驻外备注
     */
    @ApiModelProperty(value = "驻外备注", name = "outRemarks")
    private String outRemarks;

    /**
     * 驻外申报时间
     */
    @ApiModelProperty(value = "驻外申报时间", name = "outDeclareTime")
    private Date outDeclareTime;

    /**
     * 夜班时间
     */
    @ApiModelProperty(value = "夜班时间", name = "nightDuty")
    private String nightDuty;

    /**
     * 夜班工作内容
     */
    @ApiModelProperty(value = "夜班工作内容", name = "nightWorkContent")
    private String nightWorkContent;

    /**
     * 夜班补助标准（元/天）
     */
    @ApiModelProperty(value = "夜班补助标准（元/天）", name = "nightSubsidyStandard")
    private BigDecimal nightSubsidyStandard;

    /**
     * 夜班天数
     */
    @ApiModelProperty(value = "夜班天数", name = "nightDays")
    private Integer nightDays;

    /**
     * 夜班补助合计（元）
     */
    @ApiModelProperty(value = "夜班补助合计（元）", name = "nightSubsidy")
    private BigDecimal nightSubsidy;

    /**
     * 夜班备注
     */
    @ApiModelProperty(value = "夜班备注", name = "nightRemarks")
    private String nightRemarks;

    /**
     * 夜班申报时间
     */
    @ApiModelProperty(value = "夜班申报时间", name = "nightDeclareTime")
    private Date nightDeclareTime;

    /**
     * 加班时间
     */
    @ApiModelProperty(value = "加班时间", name = "overtime")
    private String overtime;

    /**
     * 加班工作内容
     */
    @ApiModelProperty(value = "加班工作内容", name = "overtimeWorkContent")
    private String overtimeWorkContent;

    /**
     * 加班补助标准（元/天）
     */
    @ApiModelProperty(value = "加班补助标准（元/天）", name = "overtimeSubsidyStandard")
    private BigDecimal overtimeSubsidyStandard;

    /**
     * 加班天数
     */
    @ApiModelProperty(value = "加班天数", name = "overtimeDays")
    private Integer overtimeDays;

    /**
     * 加班补助合计（元）
     */
    @ApiModelProperty(value = "加班补助合计（元）", name = "overtimeSubsidy")
    private BigDecimal overtimeSubsidy;

    /**
     * 加班备注
     */
    @ApiModelProperty(value = "加班备注", name = "overtimeRemarks")
    private Date overtimeRemarks;

    /**
     * 加班申报时间
     */
    @ApiModelProperty(value = "加班申报时间", name = "overtimeDeclareTime")
    private Date overtimeDeclareTime;

    /**
     * 是否发放高温补贴
     */
    @ApiModelProperty(value = "是否发放高温补贴", name = "isHeatingGrant")
    private Integer isHeatingGrant;

    /**
     * 高温补助标准（元/天）
     */
    @ApiModelProperty(value = "高温补助标准（元/天）", name = "heatingSubsidyStandart")
    private BigDecimal heatingSubsidyStandart;

    /**
     * 高温天数
     */
    @ApiModelProperty(value = "高温天数", name = "heatingDays")
    private Integer heatingDays;

    /**
     * 高温补助合计（元）
     */
    @ApiModelProperty(value = "高温补助合计（元）", name = "heatingSubsidy")
    private BigDecimal heatingSubsidy;

    /**
     * 高温备注
     */
    @ApiModelProperty(value = "高温备注", name = "heatingRemarks")
    private String heatingRemarks;

    /**
     * 高温申报时间
     */
    @ApiModelProperty(value = "高温申报时间", name = "heatingDeclareTime")
    private Date heatingDeclareTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除：0:未删除；其他时间，删除
     */
    @ApiModelProperty(value = "是否删除：0:未删除；其他时间，删除", name = "isDeleted")
    private String isDeleted;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 项目信息表uid
     * @return item_uid 项目信息表uid
     */
    public String getItemUid() {
        return itemUid;
    }

    /**
     * 项目信息表uid
     * @param itemUid 项目信息表uid
     */
    public void setItemUid(String itemUid) {
        this.itemUid = itemUid == null ? null : itemUid.trim();
    }

    /**
     * 项目人员表uid
     * @return item_member_uid 项目人员表uid
     */
    public String getItemMemberUid() {
        return itemMemberUid;
    }

    /**
     * 项目人员表uid
     * @param itemMemberUid 项目人员表uid
     */
    public void setItemMemberUid(String itemMemberUid) {
        this.itemMemberUid = itemMemberUid == null ? null : itemMemberUid.trim();
    }

    /**
     * 驻外补助标准（元/天）
     * @return out_subsidy_standard 驻外补助标准（元/天）
     */
    public BigDecimal getOutSubsidyStandard() {
        return outSubsidyStandard;
    }

    /**
     * 驻外补助标准（元/天）
     * @param outSubsidyStandard 驻外补助标准（元/天）
     */
    public void setOutSubsidyStandard(BigDecimal outSubsidyStandard) {
        this.outSubsidyStandard = outSubsidyStandard;
    }

    /**
     * 驻外天数
     * @return out_days 驻外天数
     */
    public Integer getOutDays() {
        return outDays;
    }

    /**
     * 驻外天数
     * @param outDays 驻外天数
     */
    public void setOutDays(Integer outDays) {
        this.outDays = outDays;
    }

    /**
     * 驻外补助合计（元）
     * @return out_subsidy 驻外补助合计（元）
     */
    public BigDecimal getOutSubsidy() {
        return outSubsidy;
    }

    /**
     * 驻外补助合计（元）
     * @param outSubsidy 驻外补助合计（元）
     */
    public void setOutSubsidy(BigDecimal outSubsidy) {
        this.outSubsidy = outSubsidy;
    }

    /**
     * 驻外备注
     * @return out_remarks 驻外备注
     */
    public String getOutRemarks() {
        return outRemarks;
    }

    /**
     * 驻外备注
     * @param outRemarks 驻外备注
     */
    public void setOutRemarks(String outRemarks) {
        this.outRemarks = outRemarks == null ? null : outRemarks.trim();
    }

    /**
     * 驻外申报时间
     * @return out_declare_time 驻外申报时间
     */
    public Date getOutDeclareTime() {
        return outDeclareTime;
    }

    /**
     * 驻外申报时间
     * @param outDeclareTime 驻外申报时间
     */
    public void setOutDeclareTime(Date outDeclareTime) {
        this.outDeclareTime = outDeclareTime;
    }

    /**
     * 夜班时间
     * @return night_duty 夜班时间
     */
    public String getNightDuty() {
        return nightDuty;
    }

    /**
     * 夜班时间
     * @param nightDuty 夜班时间
     */
    public void setNightDuty(String nightDuty) {
        this.nightDuty = nightDuty == null ? null : nightDuty.trim();
    }

    /**
     * 夜班工作内容
     * @return night_work_content 夜班工作内容
     */
    public String getNightWorkContent() {
        return nightWorkContent;
    }

    /**
     * 夜班工作内容
     * @param nightWorkContent 夜班工作内容
     */
    public void setNightWorkContent(String nightWorkContent) {
        this.nightWorkContent = nightWorkContent == null ? null : nightWorkContent.trim();
    }

    /**
     * 夜班补助标准（元/天）
     * @return night_subsidy_standard 夜班补助标准（元/天）
     */
    public BigDecimal getNightSubsidyStandard() {
        return nightSubsidyStandard;
    }

    /**
     * 夜班补助标准（元/天）
     * @param nightSubsidyStandard 夜班补助标准（元/天）
     */
    public void setNightSubsidyStandard(BigDecimal nightSubsidyStandard) {
        this.nightSubsidyStandard = nightSubsidyStandard;
    }

    /**
     * 夜班天数
     * @return night_days 夜班天数
     */
    public Integer getNightDays() {
        return nightDays;
    }

    /**
     * 夜班天数
     * @param nightDays 夜班天数
     */
    public void setNightDays(Integer nightDays) {
        this.nightDays = nightDays;
    }

    /**
     * 夜班补助合计（元）
     * @return night_subsidy 夜班补助合计（元）
     */
    public BigDecimal getNightSubsidy() {
        return nightSubsidy;
    }

    /**
     * 夜班补助合计（元）
     * @param nightSubsidy 夜班补助合计（元）
     */
    public void setNightSubsidy(BigDecimal nightSubsidy) {
        this.nightSubsidy = nightSubsidy;
    }

    /**
     * 夜班备注
     * @return night_remarks 夜班备注
     */
    public String getNightRemarks() {
        return nightRemarks;
    }

    /**
     * 夜班备注
     * @param nightRemarks 夜班备注
     */
    public void setNightRemarks(String nightRemarks) {
        this.nightRemarks = nightRemarks == null ? null : nightRemarks.trim();
    }

    /**
     * 夜班申报时间
     * @return night_declare_time 夜班申报时间
     */
    public Date getNightDeclareTime() {
        return nightDeclareTime;
    }

    /**
     * 夜班申报时间
     * @param nightDeclareTime 夜班申报时间
     */
    public void setNightDeclareTime(Date nightDeclareTime) {
        this.nightDeclareTime = nightDeclareTime;
    }

    /**
     * 加班时间
     * @return overtime 加班时间
     */
    public String getOvertime() {
        return overtime;
    }

    /**
     * 加班时间
     * @param overtime 加班时间
     */
    public void setOvertime(String overtime) {
        this.overtime = overtime == null ? null : overtime.trim();
    }

    /**
     * 加班工作内容
     * @return overtime_work_content 加班工作内容
     */
    public String getOvertimeWorkContent() {
        return overtimeWorkContent;
    }

    /**
     * 加班工作内容
     * @param overtimeWorkContent 加班工作内容
     */
    public void setOvertimeWorkContent(String overtimeWorkContent) {
        this.overtimeWorkContent = overtimeWorkContent == null ? null : overtimeWorkContent.trim();
    }

    /**
     * 加班补助标准（元/天）
     * @return overtime_subsidy_standard 加班补助标准（元/天）
     */
    public BigDecimal getOvertimeSubsidyStandard() {
        return overtimeSubsidyStandard;
    }

    /**
     * 加班补助标准（元/天）
     * @param overtimeSubsidyStandard 加班补助标准（元/天）
     */
    public void setOvertimeSubsidyStandard(BigDecimal overtimeSubsidyStandard) {
        this.overtimeSubsidyStandard = overtimeSubsidyStandard;
    }

    /**
     * 加班天数
     * @return overtime_days 加班天数
     */
    public Integer getOvertimeDays() {
        return overtimeDays;
    }

    /**
     * 加班天数
     * @param overtimeDays 加班天数
     */
    public void setOvertimeDays(Integer overtimeDays) {
        this.overtimeDays = overtimeDays;
    }

    /**
     * 加班补助合计（元）
     * @return overtime_subsidy 加班补助合计（元）
     */
    public BigDecimal getOvertimeSubsidy() {
        return overtimeSubsidy;
    }

    /**
     * 加班补助合计（元）
     * @param overtimeSubsidy 加班补助合计（元）
     */
    public void setOvertimeSubsidy(BigDecimal overtimeSubsidy) {
        this.overtimeSubsidy = overtimeSubsidy;
    }

    /**
     * 加班备注
     * @return overtime_remarks 加班备注
     */
    public Date getOvertimeRemarks() {
        return overtimeRemarks;
    }

    /**
     * 加班备注
     * @param overtimeRemarks 加班备注
     */
    public void setOvertimeRemarks(Date overtimeRemarks) {
        this.overtimeRemarks = overtimeRemarks;
    }

    /**
     * 加班申报时间
     * @return overtime_declare_time 加班申报时间
     */
    public Date getOvertimeDeclareTime() {
        return overtimeDeclareTime;
    }

    /**
     * 加班申报时间
     * @param overtimeDeclareTime 加班申报时间
     */
    public void setOvertimeDeclareTime(Date overtimeDeclareTime) {
        this.overtimeDeclareTime = overtimeDeclareTime;
    }

    /**
     * 是否发放高温补贴
     * @return is_heating_grant 是否发放高温补贴
     */
    public Integer getIsHeatingGrant() {
        return isHeatingGrant;
    }

    /**
     * 是否发放高温补贴
     * @param isHeatingGrant 是否发放高温补贴
     */
    public void setIsHeatingGrant(Integer isHeatingGrant) {
        this.isHeatingGrant = isHeatingGrant;
    }

    /**
     * 高温补助标准（元/天）
     * @return heating_subsidy_standart 高温补助标准（元/天）
     */
    public BigDecimal getHeatingSubsidyStandart() {
        return heatingSubsidyStandart;
    }

    /**
     * 高温补助标准（元/天）
     * @param heatingSubsidyStandart 高温补助标准（元/天）
     */
    public void setHeatingSubsidyStandart(BigDecimal heatingSubsidyStandart) {
        this.heatingSubsidyStandart = heatingSubsidyStandart;
    }

    /**
     * 高温天数
     * @return heating_days 高温天数
     */
    public Integer getHeatingDays() {
        return heatingDays;
    }

    /**
     * 高温天数
     * @param heatingDays 高温天数
     */
    public void setHeatingDays(Integer heatingDays) {
        this.heatingDays = heatingDays;
    }

    /**
     * 高温补助合计（元）
     * @return heating_subsidy 高温补助合计（元）
     */
    public BigDecimal getHeatingSubsidy() {
        return heatingSubsidy;
    }

    /**
     * 高温补助合计（元）
     * @param heatingSubsidy 高温补助合计（元）
     */
    public void setHeatingSubsidy(BigDecimal heatingSubsidy) {
        this.heatingSubsidy = heatingSubsidy;
    }

    /**
     * 高温备注
     * @return heating_remarks 高温备注
     */
    public String getHeatingRemarks() {
        return heatingRemarks;
    }

    /**
     * 高温备注
     * @param heatingRemarks 高温备注
     */
    public void setHeatingRemarks(String heatingRemarks) {
        this.heatingRemarks = heatingRemarks == null ? null : heatingRemarks.trim();
    }

    /**
     * 高温申报时间
     * @return heating_declare_time 高温申报时间
     */
    public Date getHeatingDeclareTime() {
        return heatingDeclareTime;
    }

    /**
     * 高温申报时间
     * @param heatingDeclareTime 高温申报时间
     */
    public void setHeatingDeclareTime(Date heatingDeclareTime) {
        this.heatingDeclareTime = heatingDeclareTime;
    }

    /**
     * 创建人
     * @return created_by 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 创建人
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * 创建时间
     * @return created_time 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新人
     * @return updated_by 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 更新人
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * 更新时间
     * @return updated_time 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 更新时间
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 是否删除：0:未删除；其他时间，删除
     * @return is_deleted 是否删除：0:未删除；其他时间，删除
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：0:未删除；其他时间，删除
     * @param isDeleted 是否删除：0:未删除；其他时间，删除
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}
package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class PerformanceSalary {
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
     * 岗位工资标准
     */
    @ApiModelProperty(value = "岗位工资标准", name = "postSalaryStandard")
    private BigDecimal postSalaryStandard;

    /**
     * 绩效系数
     */
    @ApiModelProperty(value = "绩效系数", name = "performanceRatio")
    private String performanceRatio;

    /**
     * 出勤月数
     */
    @ApiModelProperty(value = "出勤月数", name = "attendanceMonths")
    private Integer attendanceMonths;

    /**
     * 本次发放绩效工资
     */
    @ApiModelProperty(value = "本次发放绩效工资", name = "performanceSalary")
    private BigDecimal performanceSalary;

    /**
     * 分配阶段
     */
    @ApiModelProperty(value = "分配阶段", name = "distributeStage")
    private Byte distributeStage;

    /**
     * 批准绩效工资（元）
     */
    @ApiModelProperty(value = "批准绩效工资（元）", name = "approvePerformance")
    private BigDecimal approvePerformance;

    /**
     * 绩效工资结余（元）
     */
    @ApiModelProperty(value = "绩效工资结余（元）", name = "surplusPerformance")
    private BigDecimal surplusPerformance;

    /**
     * 本次申请工资
     */
    @ApiModelProperty(value = "本次申请工资", name = "applyFee")
    private BigDecimal applyFee;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private String declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;

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
     * 岗位工资标准
     * @return post_salary_standard 岗位工资标准
     */
    public BigDecimal getPostSalaryStandard() {
        return postSalaryStandard;
    }

    /**
     * 岗位工资标准
     * @param postSalaryStandard 岗位工资标准
     */
    public void setPostSalaryStandard(BigDecimal postSalaryStandard) {
        this.postSalaryStandard = postSalaryStandard;
    }

    /**
     * 绩效系数
     * @return performance_ratio 绩效系数
     */
    public String getPerformanceRatio() {
        return performanceRatio;
    }

    /**
     * 绩效系数
     * @param performanceRatio 绩效系数
     */
    public void setPerformanceRatio(String performanceRatio) {
        this.performanceRatio = performanceRatio == null ? null : performanceRatio.trim();
    }

    /**
     * 出勤月数
     * @return attendance_months 出勤月数
     */
    public Integer getAttendanceMonths() {
        return attendanceMonths;
    }

    /**
     * 出勤月数
     * @param attendanceMonths 出勤月数
     */
    public void setAttendanceMonths(Integer attendanceMonths) {
        this.attendanceMonths = attendanceMonths;
    }

    /**
     * 本次发放绩效工资
     * @return performance_salary 本次发放绩效工资
     */
    public BigDecimal getPerformanceSalary() {
        return performanceSalary;
    }

    /**
     * 本次发放绩效工资
     * @param performanceSalary 本次发放绩效工资
     */
    public void setPerformanceSalary(BigDecimal performanceSalary) {
        this.performanceSalary = performanceSalary;
    }

    /**
     * 分配阶段
     * @return distribute_stage 分配阶段
     */
    public Byte getDistributeStage() {
        return distributeStage;
    }

    /**
     * 分配阶段
     * @param distributeStage 分配阶段
     */
    public void setDistributeStage(Byte distributeStage) {
        this.distributeStage = distributeStage;
    }

    /**
     * 批准绩效工资（元）
     * @return approve_performance 批准绩效工资（元）
     */
    public BigDecimal getApprovePerformance() {
        return approvePerformance;
    }

    /**
     * 批准绩效工资（元）
     * @param approvePerformance 批准绩效工资（元）
     */
    public void setApprovePerformance(BigDecimal approvePerformance) {
        this.approvePerformance = approvePerformance;
    }

    /**
     * 绩效工资结余（元）
     * @return surplus_performance 绩效工资结余（元）
     */
    public BigDecimal getSurplusPerformance() {
        return surplusPerformance;
    }

    /**
     * 绩效工资结余（元）
     * @param surplusPerformance 绩效工资结余（元）
     */
    public void setSurplusPerformance(BigDecimal surplusPerformance) {
        this.surplusPerformance = surplusPerformance;
    }

    /**
     * 本次申请工资
     * @return apply_fee 本次申请工资
     */
    public BigDecimal getApplyFee() {
        return applyFee;
    }

    /**
     * 本次申请工资
     * @param applyFee 本次申请工资
     */
    public void setApplyFee(BigDecimal applyFee) {
        this.applyFee = applyFee;
    }

    /**
     * 申报时间
     * @return declare_time 申报时间
     */
    public String getDeclareTime() {
        return declareTime;
    }

    /**
     * 申报时间
     * @param declareTime 申报时间
     */
    public void setDeclareTime(String declareTime) {
        this.declareTime = declareTime == null ? null : declareTime.trim();
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
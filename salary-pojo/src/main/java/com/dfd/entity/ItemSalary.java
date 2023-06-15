package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

public class ItemSalary {
    /**
     * id
     */
    @ApiModelProperty(value = "id", name = "id")
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
     * 项目工资预算
     */
    @ApiModelProperty(value = "项目工资预算", name = "itemSalaryBudget")
    private BigDecimal itemSalaryBudget;

    /**
     * 部门批准项目工资
     */
    @ApiModelProperty(value = "部门批准项目工资", name = "approveItemFee")
    private BigDecimal approveItemFee;

    /**
     * 项目工资技术管理费额度
     */
    @ApiModelProperty(value = "项目工资技术管理费额度", name = "technologyManagerFee")
    private BigDecimal technologyManagerFee;

    /**
     * 岗位工资标准
     */
    @ApiModelProperty(value = "岗位工资标准", name = "postSalaryStandard")
    private BigDecimal postSalaryStandard;

    /**
     * 策划批准系数
     */
    @ApiModelProperty(value = "策划批准系数", name = "planApproveFactor")
    private String planApproveFactor;

    /**
     * 申报系数
     */
    @ApiModelProperty(value = "申报系数", name = "declareFactor")
    private String declareFactor;

    /**
     * 申报发放工资
     */
    @ApiModelProperty(value = "申报发放工资", name = "declareGrant")
    private BigDecimal declareGrant;

    /**
     * 项目阶段：1、设计阶段；2、采购阶段；3、制造阶段；4、安装调试阶段；5、安装验收阶段；6、终验收阶段；7、质保阶段；
     */
    @ApiModelProperty(value = "项目阶段：1、设计阶段；2、采购阶段；3、制造阶段；4、安装调试阶段；5、安装验收阶段；6、终验收阶段；7、质保阶段；", name = "itemStage")
    private String itemStage;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private Date declareTime;

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
     * id
     * @return id id
     */
    public Long getId() {
        return id;
    }

    /**
     * id
     * @param id id
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
     * 项目工资预算
     * @return item_salary_budget 项目工资预算
     */
    public BigDecimal getItemSalaryBudget() {
        return itemSalaryBudget;
    }

    /**
     * 项目工资预算
     * @param itemSalaryBudget 项目工资预算
     */
    public void setItemSalaryBudget(BigDecimal itemSalaryBudget) {
        this.itemSalaryBudget = itemSalaryBudget;
    }

    /**
     * 部门批准项目工资
     * @return approve_item_fee 部门批准项目工资
     */
    public BigDecimal getApproveItemFee() {
        return approveItemFee;
    }

    /**
     * 部门批准项目工资
     * @param approveItemFee 部门批准项目工资
     */
    public void setApproveItemFee(BigDecimal approveItemFee) {
        this.approveItemFee = approveItemFee;
    }

    /**
     * 项目工资技术管理费额度
     * @return technology_manager_fee 项目工资技术管理费额度
     */
    public BigDecimal getTechnologyManagerFee() {
        return technologyManagerFee;
    }

    /**
     * 项目工资技术管理费额度
     * @param technologyManagerFee 项目工资技术管理费额度
     */
    public void setTechnologyManagerFee(BigDecimal technologyManagerFee) {
        this.technologyManagerFee = technologyManagerFee;
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
     * 策划批准系数
     * @return plan_approve_factor 策划批准系数
     */
    public String getPlanApproveFactor() {
        return planApproveFactor;
    }

    /**
     * 策划批准系数
     * @param planApproveFactor 策划批准系数
     */
    public void setPlanApproveFactor(String planApproveFactor) {
        this.planApproveFactor = planApproveFactor == null ? null : planApproveFactor.trim();
    }

    /**
     * 申报系数
     * @return declare_factor 申报系数
     */
    public String getDeclareFactor() {
        return declareFactor;
    }

    /**
     * 申报系数
     * @param declareFactor 申报系数
     */
    public void setDeclareFactor(String declareFactor) {
        this.declareFactor = declareFactor == null ? null : declareFactor.trim();
    }

    /**
     * 申报发放工资
     * @return declare_grant 申报发放工资
     */
    public BigDecimal getDeclareGrant() {
        return declareGrant;
    }

    /**
     * 申报发放工资
     * @param declareGrant 申报发放工资
     */
    public void setDeclareGrant(BigDecimal declareGrant) {
        this.declareGrant = declareGrant;
    }

    /**
     * 申报时间
     * @return declare_time 申报时间
     */
    public Date getDeclareTime() {
        return declareTime;
    }

    /**
     * 申报时间
     * @param declareTime 申报时间
     */
    public void setDeclareTime(Date declareTime) {
        this.declareTime = declareTime;
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
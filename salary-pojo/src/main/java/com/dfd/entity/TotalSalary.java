package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class TotalSalary {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private String id;

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
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "department")
    private String department;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number")
    private String number;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", name = "name")
    private String name;

    /**
     * 工资发放日期
     */
    @ApiModelProperty(value = "工资发放日期", name = "payday")
    private String payday;

    /**
     * 项目绩效工资合计
     */
    @ApiModelProperty(value = "项目绩效工资合计", name = "performanceTotal")
    private String performanceTotal;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designTotal")
    private String designTotal;

    /**
     * 投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "tenderTotal")
    private String tenderTotal;

    /**
     * 科研工资
     */
    @ApiModelProperty(value = "科研工资", name = "scientificTotal")
    private String scientificTotal;

    /**
     * 所室
     */
    @ApiModelProperty(value = "所室", name = "office")
    private String office;

    /**
     * 专岗基础工资
     */
    @ApiModelProperty(value = "专岗基础工资", name = "specialBaseFee")
    private String specialBaseFee;

    /**
     * 专岗工资系数最大值
     */
    @ApiModelProperty(value = "专岗工资系数最大值", name = "specialMaxCoefficient")
    private String specialMaxCoefficient;

    /**
     * 专岗津贴标准工资
     */
    @ApiModelProperty(value = "专岗津贴标准工资", name = "standardSubsidy")
    private String standardSubsidy;

    /**
     * 当月专岗津贴系数
     */
    @ApiModelProperty(value = "当月专岗津贴系数", name = "subsidyCoefficient")
    private String subsidyCoefficient;

    /**
     * 当月专岗津贴计划发放
     */
    @ApiModelProperty(value = "当月专岗津贴计划发放", name = "planSubsidy")
    private String planSubsidy;

    /**
     * 当月专岗津贴考核工资
     */
    @ApiModelProperty(value = "当月专岗津贴考核工资", name = "checkSubsidy")
    private String checkSubsidy;

    /**
     * 当月专岗津贴实际发放
     */
    @ApiModelProperty(value = "当月专岗津贴实际发放", name = "realitySubsidy")
    private String realitySubsidy;

    /**
     * 当月项目工资实际发放
     */
    @ApiModelProperty(value = "当月项目工资实际发放", name = "realitySalary")
    private String realitySalary;

    /**
     * 当月项目工资应发
     */
    @ApiModelProperty(value = "当月项目工资应发", name = "planSalary")
    private String planSalary;

    /**
     * 当月项目考评工资
     */
    @ApiModelProperty(value = "当月项目考评工资", name = "checkSalary")
    private String checkSalary;

    /**
     * 项目工资系数合计
     */
    @ApiModelProperty(value = "项目工资系数合计", name = "coefficientTotal")
    private String coefficientTotal;

    /**
     * 驻外补助
     */
    @ApiModelProperty(value = "驻外补助", name = "outSubsidy")
    private String outSubsidy;

    /**
     * 夜班补助
     */
    @ApiModelProperty(value = "夜班补助", name = "nightSubsidy")
    private String nightSubsidy;

    /**
     * 加班补助
     */
    @ApiModelProperty(value = "加班补助", name = "overtimeSubsidy")
    private String overtimeSubsidy;

    /**
     * 高温补助
     */
    @ApiModelProperty(value = "高温补助", name = "heatingSubsidy")
    private String heatingSubsidy;

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
     * 主键
     * @return id 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * 项目名称
     * @return item_name 项目名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 项目名称
     * @param itemName 项目名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 部门
     * @return department 部门
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 部门
     * @param department 部门
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 工号
     * @return number 工号
     */
    public String getNumber() {
        return number;
    }

    /**
     * 工号
     * @param number 工号
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 工资发放日期
     * @return payday 工资发放日期
     */
    public String getPayday() {
        return payday;
    }

    /**
     * 工资发放日期
     * @param payday 工资发放日期
     */
    public void setPayday(String payday) {
        this.payday = payday == null ? null : payday.trim();
    }

    /**
     * 项目绩效工资合计
     * @return performance_total 项目绩效工资合计
     */
    public String getPerformanceTotal() {
        return performanceTotal;
    }

    /**
     * 项目绩效工资合计
     * @param performanceTotal 项目绩效工资合计
     */
    public void setPerformanceTotal(String performanceTotal) {
        this.performanceTotal = performanceTotal == null ? null : performanceTotal.trim();
    }

    /**
     * 设计工资
     * @return design_total 设计工资
     */
    public String getDesignTotal() {
        return designTotal;
    }

    /**
     * 设计工资
     * @param designTotal 设计工资
     */
    public void setDesignTotal(String designTotal) {
        this.designTotal = designTotal == null ? null : designTotal.trim();
    }

    /**
     * 投标工资
     * @return tender_total 投标工资
     */
    public String getTenderTotal() {
        return tenderTotal;
    }

    /**
     * 投标工资
     * @param tenderTotal 投标工资
     */
    public void setTenderTotal(String tenderTotal) {
        this.tenderTotal = tenderTotal == null ? null : tenderTotal.trim();
    }

    /**
     * 科研工资
     * @return scientific_total 科研工资
     */
    public String getScientificTotal() {
        return scientificTotal;
    }

    /**
     * 科研工资
     * @param scientificTotal 科研工资
     */
    public void setScientificTotal(String scientificTotal) {
        this.scientificTotal = scientificTotal == null ? null : scientificTotal.trim();
    }

    /**
     * 所室
     * @return office 所室
     */
    public String getOffice() {
        return office;
    }

    /**
     * 所室
     * @param office 所室
     */
    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    /**
     * 专岗基础工资
     * @return special_base_fee 专岗基础工资
     */
    public String getSpecialBaseFee() {
        return specialBaseFee;
    }

    /**
     * 专岗基础工资
     * @param specialBaseFee 专岗基础工资
     */
    public void setSpecialBaseFee(String specialBaseFee) {
        this.specialBaseFee = specialBaseFee == null ? null : specialBaseFee.trim();
    }

    /**
     * 专岗工资系数最大值
     * @return special_max_coefficient 专岗工资系数最大值
     */
    public String getSpecialMaxCoefficient() {
        return specialMaxCoefficient;
    }

    /**
     * 专岗工资系数最大值
     * @param specialMaxCoefficient 专岗工资系数最大值
     */
    public void setSpecialMaxCoefficient(String specialMaxCoefficient) {
        this.specialMaxCoefficient = specialMaxCoefficient == null ? null : specialMaxCoefficient.trim();
    }

    /**
     * 专岗津贴标准工资
     * @return standard_subsidy 专岗津贴标准工资
     */
    public String getStandardSubsidy() {
        return standardSubsidy;
    }

    /**
     * 专岗津贴标准工资
     * @param standardSubsidy 专岗津贴标准工资
     */
    public void setStandardSubsidy(String standardSubsidy) {
        this.standardSubsidy = standardSubsidy == null ? null : standardSubsidy.trim();
    }

    /**
     * 当月专岗津贴系数
     * @return subsidy_coefficient 当月专岗津贴系数
     */
    public String getSubsidyCoefficient() {
        return subsidyCoefficient;
    }

    /**
     * 当月专岗津贴系数
     * @param subsidyCoefficient 当月专岗津贴系数
     */
    public void setSubsidyCoefficient(String subsidyCoefficient) {
        this.subsidyCoefficient = subsidyCoefficient == null ? null : subsidyCoefficient.trim();
    }

    /**
     * 当月专岗津贴计划发放
     * @return plan_subsidy 当月专岗津贴计划发放
     */
    public String getPlanSubsidy() {
        return planSubsidy;
    }

    /**
     * 当月专岗津贴计划发放
     * @param planSubsidy 当月专岗津贴计划发放
     */
    public void setPlanSubsidy(String planSubsidy) {
        this.planSubsidy = planSubsidy == null ? null : planSubsidy.trim();
    }

    /**
     * 当月专岗津贴考核工资
     * @return check_subsidy 当月专岗津贴考核工资
     */
    public String getCheckSubsidy() {
        return checkSubsidy;
    }

    /**
     * 当月专岗津贴考核工资
     * @param checkSubsidy 当月专岗津贴考核工资
     */
    public void setCheckSubsidy(String checkSubsidy) {
        this.checkSubsidy = checkSubsidy == null ? null : checkSubsidy.trim();
    }

    /**
     * 当月专岗津贴实际发放
     * @return reality_subsidy 当月专岗津贴实际发放
     */
    public String getRealitySubsidy() {
        return realitySubsidy;
    }

    /**
     * 当月专岗津贴实际发放
     * @param realitySubsidy 当月专岗津贴实际发放
     */
    public void setRealitySubsidy(String realitySubsidy) {
        this.realitySubsidy = realitySubsidy == null ? null : realitySubsidy.trim();
    }

    /**
     * 当月项目工资实际发放
     * @return reality_salary 当月项目工资实际发放
     */
    public String getRealitySalary() {
        return realitySalary;
    }

    /**
     * 当月项目工资实际发放
     * @param realitySalary 当月项目工资实际发放
     */
    public void setRealitySalary(String realitySalary) {
        this.realitySalary = realitySalary == null ? null : realitySalary.trim();
    }

    /**
     * 当月项目工资应发
     * @return plan_salary 当月项目工资应发
     */
    public String getPlanSalary() {
        return planSalary;
    }

    /**
     * 当月项目工资应发
     * @param planSalary 当月项目工资应发
     */
    public void setPlanSalary(String planSalary) {
        this.planSalary = planSalary == null ? null : planSalary.trim();
    }

    /**
     * 当月项目考评工资
     * @return check_salary 当月项目考评工资
     */
    public String getCheckSalary() {
        return checkSalary;
    }

    /**
     * 当月项目考评工资
     * @param checkSalary 当月项目考评工资
     */
    public void setCheckSalary(String checkSalary) {
        this.checkSalary = checkSalary == null ? null : checkSalary.trim();
    }

    /**
     * 项目工资系数合计
     * @return coefficient_total 项目工资系数合计
     */
    public String getCoefficientTotal() {
        return coefficientTotal;
    }

    /**
     * 项目工资系数合计
     * @param coefficientTotal 项目工资系数合计
     */
    public void setCoefficientTotal(String coefficientTotal) {
        this.coefficientTotal = coefficientTotal == null ? null : coefficientTotal.trim();
    }

    /**
     * 驻外补助
     * @return out_subsidy 驻外补助
     */
    public String getOutSubsidy() {
        return outSubsidy;
    }

    /**
     * 驻外补助
     * @param outSubsidy 驻外补助
     */
    public void setOutSubsidy(String outSubsidy) {
        this.outSubsidy = outSubsidy == null ? null : outSubsidy.trim();
    }

    /**
     * 夜班补助
     * @return night_subsidy 夜班补助
     */
    public String getNightSubsidy() {
        return nightSubsidy;
    }

    /**
     * 夜班补助
     * @param nightSubsidy 夜班补助
     */
    public void setNightSubsidy(String nightSubsidy) {
        this.nightSubsidy = nightSubsidy == null ? null : nightSubsidy.trim();
    }

    /**
     * 加班补助
     * @return overtime_subsidy 加班补助
     */
    public String getOvertimeSubsidy() {
        return overtimeSubsidy;
    }

    /**
     * 加班补助
     * @param overtimeSubsidy 加班补助
     */
    public void setOvertimeSubsidy(String overtimeSubsidy) {
        this.overtimeSubsidy = overtimeSubsidy == null ? null : overtimeSubsidy.trim();
    }

    /**
     * 高温补助
     * @return heating_subsidy 高温补助
     */
    public String getHeatingSubsidy() {
        return heatingSubsidy;
    }

    /**
     * 高温补助
     * @param heatingSubsidy 高温补助
     */
    public void setHeatingSubsidy(String heatingSubsidy) {
        this.heatingSubsidy = heatingSubsidy == null ? null : heatingSubsidy.trim();
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
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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
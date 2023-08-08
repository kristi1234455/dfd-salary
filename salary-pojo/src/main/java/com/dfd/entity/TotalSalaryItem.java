package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("total_salary_item")
public class TotalSalaryItem {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

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
     * 合同子项号
     */
    @ApiModelProperty(value = "合同子项号", name = "subItemNumber")
    private String subItemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumbe")
    private String virtualSubItemNumbe;

    /**
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    private String itemNumber;

    /**
     * 合同金额(万元）
     */
    @ApiModelProperty(value = "合同金额(万元）", name = "agreementMoney")
    private BigDecimal agreementMoney;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private String itemManager;

    /**
     * 投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private String bidDirector;

    /**
     * 设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private String designManager;

    /**
     * 科研经理
     */
    @ApiModelProperty(value = "科研经理", name = "scientificManager")
    private String scientificManager;

    /**
     * 岗位工资标准
     */
    @ApiModelProperty(value = "岗位工资标准", name = "postSalaryStandard")
    private BigDecimal postSalaryStandard;

    /**
     * 实际申报批准系数
     */
    @ApiModelProperty(value = "实际申报批准系数", name = "declareFactor")
    private String declareFactor;

    /**
     * 考核工资应发
     */
    @ApiModelProperty(value = "考核工资应发", name = "checkPlanSalary")
    private BigDecimal checkPlanSalary;

    /**
     * 考核工资
     */
    @ApiModelProperty(value = "考核工资", name = "checkSalary")
    private BigDecimal checkSalary;

    /**
     * 申报发放工资
     */
    @ApiModelProperty(value = "申报发放工资", name = "declareGrant")
    private BigDecimal declareGrant;

    /**
     * 绩效工资
     */
    @ApiModelProperty(value = "绩效工资", name = "performanceSalary")
    private BigDecimal performanceSalary;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designSalary")
    private BigDecimal designSalary;

    /**
     * 投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "bidSalary")
    private BigDecimal bidSalary;

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
     * 项目工资申报日期
     */
    @ApiModelProperty(value = "项目工资申报日期", name = "declareTime")
    private Date declareTime;

    /**
     * 项目工资申报金额（元）
     */
    @ApiModelProperty(value = "项目工资申报金额（元）", name = "totalSalary")
    private BigDecimal totalSalary;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "mark")
    private String mark;

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



}
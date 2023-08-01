package com.dfd.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class TotalSalaryItemInfoVO implements Serializable {

    private static final long serialVersionUID = -1325759829897239212L;

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
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    private String itemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumber")
    private String virtualSubItemNumber;

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
    private String checkPlanSalary;

    /**
     * 考核工资
     */
    @ApiModelProperty(value = "考核工资", name = "checkSalary")
    private String checkSalary;

    /**
     * 申报发放工资
     */
    @ApiModelProperty(value = "申报发放工资", name = "declareGrant")
    private BigDecimal declareGrant;

    /**
     * 本次发放绩效工资
     */
    @ApiModelProperty(value = "本次发放绩效工资", name = "performanceSalary")
    private BigDecimal performanceSalary;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资",name = "designSalary")
    private BigDecimal designSalary;

    /**
     * 投标工资
     */
    @ApiModelProperty(value = "投标工资",name = "bidFee")
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

}
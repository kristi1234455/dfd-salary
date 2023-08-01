package com.dfd.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class TotalSalaryBasicInfoVO implements Serializable {

    private static final long serialVersionUID = -1325759829897239212L;

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
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "room")
    private String room;
    /**
     * 部门所室
     */
    @ApiModelProperty(value = "部门所室", name = "department")
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
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private Date declareTime;

}
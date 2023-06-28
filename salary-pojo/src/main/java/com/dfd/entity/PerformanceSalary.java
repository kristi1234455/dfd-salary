package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@TableName("performance_salary")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PerformanceSalary {
    /**
     * 主键
     */
    @TableId(value="id",type = IdType.AUTO)
    @ApiModelProperty(value = "主键", name = "id")
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


}
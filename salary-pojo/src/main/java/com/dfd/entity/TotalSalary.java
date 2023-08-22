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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("total_salary")
@Accessors(chain = true)
public class TotalSalary {
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
     * 工资发放日期
     */
    @ApiModelProperty(value = "工资发放日期", name = "payday")
    private String payday;

    /**
     * 岗位工资发放
     */
    @ApiModelProperty(value = "岗位工资发放", name = "declareGrant")
    private BigDecimal declareGrant;

    /**
     * 项目绩效工资合计
     */
    @ApiModelProperty(value = "项目绩效工资合计", name = "performanceTotal")
    private BigDecimal performanceTotal;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designTotal")
    private BigDecimal designTotal;

    /**
     * 投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "tenderTotal")
    private BigDecimal tenderTotal;

    /**
     * 科研工资
     */
    @ApiModelProperty(value = "科研工资", name = "scientificTotal")
    private BigDecimal scientificTotal;

    /**
     * 专岗申报时间
     */
    @ApiModelProperty(value = "专岗申报时间", name = "specialDeclareTime")
    private String specialDeclareTime;

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
    private BigDecimal outSubsidy;

    /**
     * 夜班补助
     */
    @ApiModelProperty(value = "夜班补助", name = "nightSubsidy")
    private BigDecimal nightSubsidy;

    /**
     * 加班补助
     */
    @ApiModelProperty(value = "加班补助", name = "overtimeSubsidy")
    private BigDecimal overtimeSubsidy;

    /**
     * 高温补助
     */
    @ApiModelProperty(value = "高温补助", name = "heatingSubsidy")
    private BigDecimal heatingSubsidy;

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


}
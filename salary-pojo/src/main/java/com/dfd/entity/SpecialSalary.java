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

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("special_salary")
public class SpecialSalary {
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
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

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
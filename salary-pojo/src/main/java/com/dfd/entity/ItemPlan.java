package com.dfd.entity;

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
@TableName("item_plan")
public class ItemPlan {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目表uid
     */
    @ApiModelProperty(value = "项目表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 设计阶段系数
     */
    @ApiModelProperty(value = "设计阶段系数", name = "designCoefficient")
    private BigDecimal designCoefficient;

    /**
     * 采购阶段系数
     */
    @ApiModelProperty(value = "采购阶段系数", name = "purchaseCoefficient")
    private BigDecimal purchaseCoefficient;

    /**
     * 制造阶段系数
     */
    @ApiModelProperty(value = "制造阶段系数", name = "manufactureCoefficient")
    private BigDecimal manufactureCoefficient;

    /**
     * 安装调试阶段系数
     */
    @ApiModelProperty(value = "安装调试阶段系数", name = "installationCoefficient")
    private BigDecimal installationCoefficient;

    /**
     * 安装验收阶段系数
     */
    @ApiModelProperty(value = "安装验收阶段系数", name = "inspectionCoefficient")
    private BigDecimal inspectionCoefficient;

    /**
     * 终验收阶段系数
     */
    @ApiModelProperty(value = "终验收阶段系数", name = "finalCoefficient")
    private BigDecimal finalCoefficient;

    /**+
     * 质保阶段系数
     */
    @ApiModelProperty(value = "质保阶段系数", name = "guaranteeCoefficient")
    private BigDecimal guaranteeCoefficient;
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
package com.dfd.vo;

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
public class ItemPlanInfoVO {

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
     * 名字
     */
    @ApiModelProperty(value = "名字", name = "name")
    private String name;

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
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
}
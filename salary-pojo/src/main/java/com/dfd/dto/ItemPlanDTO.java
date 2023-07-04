package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 14:19
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemPlanDTO implements Serializable {

    private static final long serialVersionUID = 2517652235173564026L;

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
     * 设计阶段系数
     */
    @ApiModelProperty(value = "设计阶段系数", name = "designCoefficient")
    private Integer designCoefficient;

    /**
     * 采购阶段系数
     */
    @ApiModelProperty(value = "采购阶段系数", name = "purchaseCoefficient")
    private Integer purchaseCoefficient;

    /**
     * 制造阶段系数
     */
    @ApiModelProperty(value = "制造阶段系数", name = "manufactureCoefficient")
    private Integer manufactureCoefficient;

    /**
     * 安装调试阶段系数
     */
    @ApiModelProperty(value = "安装调试阶段系数", name = "installationCoefficient")
    private Integer installationCoefficient;

    /**
     * 安装验收阶段系数
     */
    @ApiModelProperty(value = "安装验收阶段系数", name = "inspectionCoefficient")
    private Integer inspectionCoefficient;

    /**
     * 终验收阶段系数
     */
    @ApiModelProperty(value = "终验收阶段系数", name = "finalCoefficient")
    private Integer finalCoefficient;

    /**
     * 质保阶段系数
     */
    @ApiModelProperty(value = "质保阶段系数", name = "guaranteeCoefficient")
    private Integer guaranteeCoefficient;
}

package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemPlanUpDTO implements Serializable {
    private static final long serialVersionUID = 1535395190750036262L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid", required = true)
    @NotNull(message = "主键不能为空")
    private String uid;

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

    /**+
     * 质保阶段系数
     */
    @ApiModelProperty(value = "质保阶段系数", name = "guaranteeCoefficient")
    private Integer guaranteeCoefficient;


}
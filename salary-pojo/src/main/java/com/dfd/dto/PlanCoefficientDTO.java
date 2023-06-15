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
@ApiModel(value = "阶段策划系数对象")
public class PlanCoefficientDTO implements Serializable {

    private static final long serialVersionUID = 2517652235173564026L;
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
     * 设计阶段
     */
    @ApiModelProperty(value = "设计阶段", name = "designStage")
    private String designStage;

    /**
     * 采购阶段
     */
    @ApiModelProperty(value = "采购阶段", name = "procureStage")
    private String procureStage;

    /**
     * 制造阶段
     */
    @ApiModelProperty(value = "制造阶段", name = "manufactureStage")
    private String manufactureStage;

    /**
     * 安装调试阶段
     */
    @ApiModelProperty(value = "安装调试阶段", name = "debugStage")
    private String debugStage;

    /**
     * 安装验收阶段
     */
    @ApiModelProperty(value = "安装验收阶段", name = "checkStage")
    private String checkStage;

    /**
     * 终验收阶段
     */
    @ApiModelProperty(value = "终验收阶段", name = "finalStage")
    private String finalCheckStage;

    /**
     * 质保阶段
     */
    @ApiModelProperty(value = "质保阶段", name = "qualityGuaranteeStage")
    private String qualityGuaranteeStage;
}

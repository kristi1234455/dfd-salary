package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yy
 * @date 2023/6/12 16:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class PerformanceSalaryInfoVO implements Serializable {

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目人员Uid
     */
    @ApiModelProperty(value = "项目人员Uid", name = "itemMemberUid", required = true)
    @NotBlank(message = "项目人员Uid不能为空")
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
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
}

package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yy
 * @date 2023/6/12 17:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class PerformanceSalaryAddDTO implements Serializable {
    private static final long serialVersionUID = -6754740015714616110L;


    /**
     * 项目Uid
     */
    @ApiModelProperty(value = "项目Uid", name = "itemUid", required = true)
    @NotBlank(message = "项目Uid不能为空")
    private String itemUid;

    /**
     * 项目人员Uid
     */
    @ApiModelProperty(value = "项目人员Uid", name = "itemMemberUid",required = true)
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
    @NotNull(message = "岗位工资标准不能为空")
    private BigDecimal postSalaryStandard;

    /**
     * 绩效系数
     */
    @ApiModelProperty(value = "绩效系数", name = "performanceRatio")
    @NotBlank(message = "绩效系数不能为空")
    private String performanceRatio;

    /**
     * 出勤月数
     */
    @ApiModelProperty(value = "出勤月数", name = "attendanceMonths")
    @NotNull(message = "出勤月数不能为空")
    private Integer attendanceMonths;

    /**
     * 本次发放绩效工资
     */
    @ApiModelProperty(value = "本次发放绩效工资", name = "performanceSalary")
    private BigDecimal performanceSalary;
    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime",required = true)
    @NotBlank(message = "申报时间不能为空")
    private String declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
}

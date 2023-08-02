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
 * @date 2023/6/14 14:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SubsidyOutAddDTO implements Serializable {

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid",required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目成员uid
     */
    @ApiModelProperty(value = "项目成员uid", name = "itemMemberUid",required = true)
    @NotBlank(message = "项目成员uid不能为空")
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
     * 驻外补助标准（元/天）
     */
    @ApiModelProperty(value = "驻外补助标准（元/天）", name = "outSubsidyStandard")
    private BigDecimal outSubsidyStandard;

    /**
     * 驻外天数
     */
    @ApiModelProperty(value = "驻外天数", name = "outDays")
    @NotNull(message = "驻外天数不能为空")
    private Integer outDays;

    /**
     * 驻外补助合计（元）
     */
    @ApiModelProperty(value = "驻外补助合计（元）", name = "outSubsidy")
    private BigDecimal outSubsidy;

    /**
     * 驻外备注
     */
    @ApiModelProperty(value = "驻外备注", name = "outRemarks")
    private String outRemarks;

    /**
     * 驻外申报时间
     */
    @ApiModelProperty(value = "驻外申报时间", name = "outDeclareTime")
    private String outDeclareTime;
}

package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/14 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class SubsidyHeatingInfoVO {
    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid")
    private String itemUid;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目成员uid
     */
    @ApiModelProperty(value = "项目成员uid", name = "itemMemberUid")
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
     * 是否发放高温补贴
     */
    @ApiModelProperty(value = "是否发放高温补贴", name = "isHeatingGrant")
    private Integer isHeatingGrant;

    /**
     * 高温补助标准（元/天）
     */
    @ApiModelProperty(value = "高温补助标准（元/天）", name = "heatingSubsidyStandard")
    private BigDecimal heatingSubsidyStandard;

    /**
     * 高温天数
     */
    @ApiModelProperty(value = "高温天数", name = "heatingDays")
    private Integer heatingDays;

    /**
     * 高温补助合计（元）
     */
    @ApiModelProperty(value = "高温补助合计（元）", name = "heatingSubsidy")
    private BigDecimal heatingSubsidy;

    /**
     * 高温备注
     */
    @ApiModelProperty(value = "高温备注", name = "heatingRemarks")
    private String heatingRemarks;

    /**
     * 高温申报时间
     */
    @ApiModelProperty(value = "高温申报时间", name = "heatingDeclareTime")
    private Date heatingDeclareTime;

}

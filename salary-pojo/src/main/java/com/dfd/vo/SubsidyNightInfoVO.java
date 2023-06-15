package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/14 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "夜班补助信息对象",description = "封装夜班补助信息对象")
public class SubsidyNightInfoVO implements Serializable {
    private static final long serialVersionUID = -3430081037095527844L;

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
     * 夜班时间
     */
    @ApiModelProperty(value = "夜班时间", name = "nightDuty")
    private String nightDuty;

    /**
     * 夜班工作内容
     */
    @ApiModelProperty(value = "夜班工作内容", name = "nightWorkContent")
    private String nightWorkContent;

    /**
     * 夜班补助标准（元/天）
     */
    @ApiModelProperty(value = "夜班补助标准（元/天）", name = "nightSubsidyStandard")
    private BigDecimal nightSubsidyStandard;

    /**
     * 夜班天数
     */
    @ApiModelProperty(value = "夜班天数", name = "nightDays")
    private Integer nightDays;

    /**
     * 夜班补助合计（元）
     */
    @ApiModelProperty(value = "夜班补助合计（元）", name = "nightSubsidy")
    private BigDecimal nightSubsidy;

    /**
     * 夜班备注
     */
    @ApiModelProperty(value = "夜班备注", name = "nightRemarks")
    private String nightRemarks;

    /**
     * 夜班申报时间
     */
    @ApiModelProperty(value = "夜班申报时间", name = "nightDeclareTime")
    private Date nightDeclareTime;

}

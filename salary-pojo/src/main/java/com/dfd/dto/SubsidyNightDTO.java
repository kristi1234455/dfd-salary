package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/14 11:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "补助信息对象",description = "封装补助信息对象")
public class SubsidyNightDTO implements Serializable {

    private static final long serialVersionUID = 6939688258716749432L;
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

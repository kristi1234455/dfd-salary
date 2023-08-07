package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("subsidy_night")
@Accessors(chain = true)
public class SubsidyNight {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

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
    private String nightDeclareTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除：0:未删除；其他时间，删除
     */
    @ApiModelProperty(value = "是否删除：0:未删除；其他时间，删除", name = "isDeleted")
    private String isDeleted;

}
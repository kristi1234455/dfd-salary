package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("subsidy")
@Accessors(chain = true)
public class Subsidy {
    /**
     * 主键
     */
    @TableId(value="id",type = IdType.AUTO)
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
     * 驻外补助标准（元/天）
     */
    @ApiModelProperty(value = "驻外补助标准（元/天）", name = "outSubsidyStandard")
    private BigDecimal outSubsidyStandard;

    /**
     * 驻外天数
     */
    @ApiModelProperty(value = "驻外天数", name = "outDays")
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
     * 加班时间
     */
    @ApiModelProperty(value = "加班时间", name = "overtime")
    private String overtime;

    /**
     * 加班工作内容
     */
    @ApiModelProperty(value = "加班工作内容", name = "overtimeWorkContent")
    private String overtimeWorkContent;

    /**
     * 加班补助标准（元/天）
     */
    @ApiModelProperty(value = "加班补助标准（元/天）", name = "overtimeSubsidyStandard")
    private BigDecimal overtimeSubsidyStandard;

    /**
     * 加班天数
     */
    @ApiModelProperty(value = "加班天数", name = "overtimeDays")
    private Integer overtimeDays;

    /**
     * 加班补助合计（元）
     */
    @ApiModelProperty(value = "加班补助合计（元）", name = "overtimeSubsidy")
    private BigDecimal overtimeSubsidy;

    /**
     * 加班备注
     */
    @ApiModelProperty(value = "加班备注", name = "overtimeRemarks")
    private Date overtimeRemarks;

    /**
     * 加班申报时间
     */
    @ApiModelProperty(value = "加班申报时间", name = "overtimeDeclareTime")
    private String overtimeDeclareTime;

    /**
     * 是否发放高温补贴
     */
    @ApiModelProperty(value = "是否发放高温补贴", name = "isHeatingGrant")
    private Integer isHeatingGrant;

    /**
     * 高温补助标准（元/天）
     */
    @ApiModelProperty(value = "高温补助标准（元/天）", name = "heatingSubsidyStandart")
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
    private String heatingDeclareTime;

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
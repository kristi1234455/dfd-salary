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

import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("subsidy_out")
@Accessors(chain = true)
public class SubsidyOut {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    @TableId(value="id",type = IdType.AUTO)
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
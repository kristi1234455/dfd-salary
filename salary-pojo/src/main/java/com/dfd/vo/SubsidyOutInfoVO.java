package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/14 14:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class SubsidyOutInfoVO implements Serializable {

    private static final long serialVersionUID = 2806852272106196689L;
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
    private Date outDeclareTime;
}

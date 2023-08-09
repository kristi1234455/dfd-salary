package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yy
 * @date 2023/6/9 15:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class DesignSalaryInfoVO implements Serializable {
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid", required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;

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
     * 大类专业
     */
    @ApiModelProperty(value = "大类专业",name = "mainMajor")
    private String mainMajor;

    /**
     * 小类专业
     */
    @ApiModelProperty(value = "小类专业",name = "minorMajor")
    private String minorMajor;

    /**
     * 设计经理工资
     */
    @ApiModelProperty(value = "设计经理工资",name = "designManager")
    private BigDecimal designManager;

    /**
     * 专业负责人工资
     */
    @ApiModelProperty(value = "专业负责人工资",name = "director")
    private BigDecimal director;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资",name = "design")
    private BigDecimal design;

    /**
     * 校对工资
     */
    @ApiModelProperty(value = "校对工资",name = "proofread")
    private BigDecimal proofread;

    /**
     * 审核工资
     */
    @ApiModelProperty(value = "审核工资",name = "audit")
    private BigDecimal audit;

    /**
     * 工资小计
     */
    @ApiModelProperty(value = "工资小计",name = "subtotal")
    private BigDecimal subtotal;

    /**
     * 比例统计
     */
    @ApiModelProperty(value = "比例统计",name = "ratioCensus")
    private String ratioCensus;

    /**
     * 设计工资合计
     */
    @ApiModelProperty(value = "设计工资合计",name = "total")
    private BigDecimal total;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间",name = "declareTime")
    private String declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",name = "remarks")
    private String remarks;
}

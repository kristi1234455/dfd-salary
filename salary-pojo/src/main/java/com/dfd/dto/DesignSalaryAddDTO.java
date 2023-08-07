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
 * @date 2023/6/9 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class DesignSalaryAddDTO implements Serializable {
    private static final long serialVersionUID = -2630512229883746766L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid",required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

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

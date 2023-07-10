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
 * @date 2023/6/9 11:53
 */
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ScientificSalaryInfoVO implements Serializable {
    private static final long serialVersionUID = 7259303260912686797L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
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
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private String declareTime;

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
    @ApiModelProperty(value = "大类专业", name = "mainMajor")
    private String mainMajor;

    /**
     * 小类专业
     */
    @ApiModelProperty(value = "小类专业", name = "minorMajor")
    private String minorMajor;

    /**
     * 科研经理工资
     */
    @ApiModelProperty(value = "科研经理工资", name = "scientificManager")
    private BigDecimal scientificManager;

    /**
     * 专业负责工资
     */
    @ApiModelProperty(value = "专业负责工资", name = "director")
    private BigDecimal director;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "design")
    private BigDecimal design;

    /**
     * 校对工资
     */
    @ApiModelProperty(value = "校对工资", name = "proofread")
    private BigDecimal proofread;

    /**
     * 审核工资
     */
    @ApiModelProperty(value = "审核工资", name = "audit")
    private BigDecimal audit;

    /**
     * 工资小计
     */
    @ApiModelProperty(value = "工资小计", name = "subtotal")
    private BigDecimal subtotal;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
}

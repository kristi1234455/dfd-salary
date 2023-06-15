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
 * @date 2023/3/31 17:20
 */
@ApiModel(value = "项目信息对象")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemInfoVO implements Serializable {

    private static final long serialVersionUID = 247833528975138822L;
    /**
     * uid
     */
    @ApiModelProperty(value = "项目uid", name = "uid")
    private String uid;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目开始时间
     */
    @ApiModelProperty(value = "项目开始时间", name = "itemStartTime")
    private Date itemStartTime;

    /**
     * 项目类别：1：投标项目；2：EPC项目；3：科研项目
     */
    @ApiModelProperty(value = "项目类别：1：投标项目；2：EPC项目；3：科研项目", name = "itemProperties")
    private String itemProperties;

    /**
     * 投标：投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private String bidDirector;

    /**
     * 投标：投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "bidSalary")
    private BigDecimal bidSalary;

    /**
     * 投标：已用投标工资
     */
    @ApiModelProperty(value = "已用投标工资", name = "bidUsedSalary")
    private BigDecimal bidUsedSalary;

    /**
     * ECP：技术管理费
     */
    @ApiModelProperty(value = "技术管理费", name = "technicalFee")
    private BigDecimal technicalFee;

    /**
     * ECP：已用技术管理费
     */
    @ApiModelProperty(value = "技术管理费", name = "technicalUsedFee")
    private BigDecimal technicalUsedFee;

    /**
     * ECP：项目工资
     */
    @ApiModelProperty(value = "项目工资", name = "itemTotalSalary")
    private BigDecimal itemTotalSalary;

    /**
     * ECP：已用项目工资
     */
    @ApiModelProperty(value = "已用项目工资", name = "itemUsedTotalSalary")
    private BigDecimal itemUsedTotalSalary;

    /**
     * ECP：项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private String itemManager;

    /**
     * ECP：设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private String designManager;

    /**
     * 科研：科研工资
     */
    @ApiModelProperty(value = "科研工资", name = "scientificSalary")
    private BigDecimal scientificSalary;

    /**
     * 科研：已用科研工资
     */
    @ApiModelProperty(value = "已用科研工资", name = "scientificSalary")
    private BigDecimal scientificUsedSalary;

    /**
     * 科研：科研主持人
     */
    @ApiModelProperty(value = "科研主持人", name = "scientificManager")
    private String scientificManager;
}

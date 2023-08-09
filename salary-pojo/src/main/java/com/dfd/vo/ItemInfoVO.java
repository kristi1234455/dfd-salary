package com.dfd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2023/3/31 17:20
 */
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

//没有数据
    /**
     * 投标：投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "bidSalary")
    private BigDecimal bidSalary;
//没有数据
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
//没有数据
    /**
     * ECP：已用技术管理费
     */
    @ApiModelProperty(value = "技术管理费", name = "technicalUsedFee")
    private BigDecimal technicalUsedFee;

    /**
     * ECP：项目工资
     */
    @ApiModelProperty(value = "项目工资", name = "itemSalary")
    private BigDecimal itemSalary;
//没有数据
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

//没有数据
    /**
     * 科研：科研工资
     */
    @ApiModelProperty(value = "科研工资", name = "scientificSalary")
    private BigDecimal scientificSalary;
//没有数据
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

    /**
     * 项目绩效
     */
    @ApiModelProperty(value = "项目绩效", name = "performanceSalary")
    private BigDecimal itemPerformance;
}

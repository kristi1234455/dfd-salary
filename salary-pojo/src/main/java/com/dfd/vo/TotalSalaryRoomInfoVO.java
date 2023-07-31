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
 * @date 2023/6/12 8:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class TotalSalaryRoomInfoVO implements Serializable {
    private static final long serialVersionUID = 2812845239417651576L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "room")
    private String room;

    /**
     * 合同子项号
     */
    @ApiModelProperty(value = "合同子项号", name = "subItemNumber")
    private String subItemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumbe")
    private String virtualSubItemNumbe;

    /**
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    private String itemNumber;


    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目阶段
     */
    @ApiModelProperty(value = "项目阶段", name = "itemStage")
    private String itemStage;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemProperties")
    private String itemProperties;

    /**
     * 项目工资申报日期
     */
    @ApiModelProperty(value = "项目工资申报日期", name = "declareTime")
    private Date declareTime;

    /**
     * 项目工资申报金额（元）
     */
    @ApiModelProperty(value = "项目工资申报金额（元）", name = "totalSalary")
    private BigDecimal totalSalary;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "mark")
    private String mark;
}

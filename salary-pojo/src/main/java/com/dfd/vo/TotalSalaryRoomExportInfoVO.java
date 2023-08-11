package com.dfd.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.alibaba.excel.converters.date.DateStringConverter;
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
public class TotalSalaryRoomExportInfoVO implements Serializable {
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
    @ExcelProperty(value = "部门", order = 1)
    @ColumnWidth(30)
    private String room;

    /**
     * 合同子项号
     */
    @ApiModelProperty(value = "合同子项号", name = "subItemNumber")
    @ExcelProperty(value = "合同子项号", order = 2)
    @ColumnWidth(30)
    private String subItemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumbe")
    @ExcelProperty(value = "虚拟合同子项号", order = 3)
    @ColumnWidth(30)
    private String virtualSubItemNumbe;

    /**
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    @ExcelProperty(value = "立项号", order = 4)
    @ColumnWidth(30)
    private String itemNumber;


    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    @ExcelProperty(value = "项目名称", order = 5)
    @ColumnWidth(30)
    private String itemName;

    /**
     * 项目阶段
     */
    @ApiModelProperty(value = "项目阶段", name = "itemStage")
    @ExcelProperty(value = "阶段", order = 6)
    @ColumnWidth(30)
    private String itemStage;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemProperties")
    @ExcelProperty(value = "项目经理", order = 7)
    @ColumnWidth(30)
    private String itemManager;

    /**
     * 项目工资申报日期
     */
    @ApiModelProperty(value = "项目工资申报日期", name = "declareTime")
    @ExcelProperty(value = "项目工资申报日期", order = 8, converter = DateStringConverter.class)
    @ColumnWidth(20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date declareTime;

    /**
     * 项目工资申报金额（元）
     */
    @ApiModelProperty(value = "项目工资申报金额（元）", name = "totalSalary")
    @ExcelProperty(value = "项目工资申报金额（元）", order = 9)
    @ColumnWidth(30)
    private BigDecimal totalSalary;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "mark")
    @ExcelProperty(value = "备注", order = 10)
    @ColumnWidth(30)
    private String mark;
}

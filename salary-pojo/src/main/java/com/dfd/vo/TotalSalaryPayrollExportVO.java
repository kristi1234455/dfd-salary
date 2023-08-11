package com.dfd.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class TotalSalaryPayrollExportVO {

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
    @ColumnWidth(20)
    private String room;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number")
    @ExcelProperty(value = "工号", order = 2)
    @ColumnWidth(20)
    private String number;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", name = "name")
    @ExcelProperty(value = "姓名", order = 3)
    @ColumnWidth(20)
    private String name;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    @ExcelProperty(value = "工资发放时间", order = 4)
    @ColumnWidth(20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date declareTime;


    /**
     * 当月项目工资应发
     */
    @ApiModelProperty(value = "当月项目工资应发", name = "planSalary")
    @ExcelProperty(value = "当月项目工资应发", order = 5)
    @ColumnWidth(20)
    private String planSalary;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    @ExcelProperty(value = "备注", order = 6)
    @ColumnWidth(20)
    private String remarks;
}
package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/13 17:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SubsidyOvertimeAddDTO implements Serializable {

    private static final long serialVersionUID = -6895638585575888353L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid",required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

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
     * 加班时间
     */
    @ApiModelProperty(value = "加班时间", name = "overtime")
    private String overtime;

    /**
     * 加班工作内容
     */
    @ApiModelProperty(value = "加班工作内容", name = "overtimeWorkContent")
    private String overtimeWorkContent;

    /**
     * 加班补助标准（元/天）
     */
    @ApiModelProperty(value = "加班补助标准（元/天）", name = "overtimeSubsidyStandard")
    private BigDecimal overtimeSubsidyStandard;

    /**
     * 加班天数
     */
    @ApiModelProperty(value = "加班天数", name = "overtimeDays")
    @NotBlank(message = "加班天数不能为空")
    private Integer overtimeDays;

    /**
     * 加班补助合计（元）
     */
    @ApiModelProperty(value = "加班补助合计（元）", name = "overtimeSubsidy")
    private BigDecimal overtimeSubsidy;

    /**
     * 加班备注
     */
    @ApiModelProperty(value = "加班备注", name = "overtimeRemarks")
    private Date overtimeRemarks;

    /**
     * 加班申报时间
     */
    @ApiModelProperty(value = "加班申报时间", name = "overtimeDeclareTime")
    private String overtimeDeclareTime;

}

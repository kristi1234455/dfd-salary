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
 * @date 2023/6/13 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class SubsidyOvertimeInfoVO implements Serializable {

    private static final long serialVersionUID = 6518850371907250746L;
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
    private Date overtimeDeclareTime;
}

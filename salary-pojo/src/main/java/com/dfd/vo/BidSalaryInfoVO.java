package com.dfd.vo;

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
 * @date 2023/6/9 9:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "投标工资信息对象",description = "封装投标工资信息对象")
public class BidSalaryInfoVO implements Serializable {

    private static final long serialVersionUID = 4439318983223035254L;
    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目人员Uid
     */
    @ApiModelProperty(value = "项目人员Uid", name = "itemMemberUid",required = true)
    @NotBlank(message = "项目人员uid不能为空")
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
     * 设备类别
     */
    @ApiModelProperty(value = "设备类别", name = "deviceCategory")
    private String deviceCategory;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称", name = "deviceName")
    private String deviceName;

    /**
     * 设备产值（万元）
     */
    @ApiModelProperty(value = "设备产值（万元）", name = "deviceOutput")
    private BigDecimal deviceOutput;

    /**
     * 投标任务
     */
    @ApiModelProperty(value = "投标任务", name = "bidTask")
    private String bidTask;

    /**
     * 设计人名称
     */
    @ApiModelProperty(value = "设计人名称", name = "designer")
    private String designer;

    /**
     * 评审人
     */
    @ApiModelProperty(value = "评审人", name = "reviewer")
    private String reviewer;

    /**
     * 设计完成时间
     */
    @ApiModelProperty(value = "设计完成时间", name = "finishTime")
    private Date finishTime;

    /**
     * 分配金额
     */
    @ApiModelProperty(value = "分配金额", name = "distributeFee")
    private BigDecimal distributeFee;

    /**
     * 调整系数
     */
    @ApiModelProperty(value = "调整系数", name = "adjustedCoefficient")
    private String adjustedCoefficient;

    /**
     * 占比
     */
    @ApiModelProperty(value = "占比", name = "ratio")
    private String ratio;

    /**
     * 投标工资
     */
    @ApiModelProperty(value = "投标工资", name = "bidFee")
    private BigDecimal bidFee;

    /**
     * 分配总金额
     */
    @ApiModelProperty(value = "分配总金额", name = "distributeTotalFee")
    private BigDecimal distributeTotalFee;
}

package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("bid_salary")
public class BidSalary {
    /**
     * 主键
     */
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;
    /**
     * 投标计划表uid
     */
    private String bidUid;

    /**
     * 项目人员表uid
     */
    private String itemMemberUid;

    /**
     * 设备类别
     */
    private String deviceCategory;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备产值（万元）
     */
    private BigDecimal deviceOutput;

    /**
     * 投标任务
     */
    private String bidTask;

    /**
     * 设计人名称
     */
    private String designer;

    /**
     * 评审人
     */
    private String reviewer;

    /**
     * 设计完成时间
     */
    private Date finishTime;

    /**
     * 分配金额
     */
    private BigDecimal distributeFee;

    /**
     * 调整系数
     */
    private String adjustedCoefficient;

    /**
     * 占比
     */
    private String ratio;

    /**
     * 投标工资
     */
    private BigDecimal bidFee;

    /**
     * 分配总金额
     */
    private BigDecimal distributeTotalFee;

    /**
     * 申报时间
     */
    private Date declareTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除，0，未删除；其他：删除
     */
    private String isDeleted;
}
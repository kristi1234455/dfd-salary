package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("scientific_salary")
@Accessors(chain = true)
public class ScientificSalary {
    /**
     * id
     */
    @TableId(value="id",type = IdType.AUTO)
    @ApiModelProperty(value = "id",name = "id")
    private Integer id;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;
    /**
     * 科研计划表uid
     */
    @ApiModelProperty(value = "科研计划表uid",name = "scientificPlanUid")
    private String scientificPlanUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid",name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 部门技术服务费
     */
    @ApiModelProperty(value = "部门技术服务费",name = "technicalFee")
    private BigDecimal technicalFee;

    /**
     * 大类专业
     */
    @ApiModelProperty(value = "大类专业",name = "mainMajor")
    private String mainMajor;

    /**
     * 小类专业
     */
    @ApiModelProperty(value = "小类专业",name = "minorMajor")
    private String minorMajor;

    /**
     * 科研经理工资
     */
    @ApiModelProperty(value = "科研经理工资",name = "scientificManager")
    private BigDecimal scientificManager;

    /**
     * 专业负责工资
     */
    @ApiModelProperty(value = "专业负责工资",name = "director")
    private BigDecimal director;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资",name = "design")
    private BigDecimal design;

    /**
     * 校对工资
     */
    @ApiModelProperty(value = "校对工资",name = "proofread")
    private BigDecimal proofread;

    /**
     * 审核工资
     */
    @ApiModelProperty(value = "审核工资",name = "audit")
    private BigDecimal audit;

    /**
     * 工资小计
     */
    @ApiModelProperty(value = "工资小计",name = "subtotal")
    private BigDecimal subtotal;

    /**
     * 比例统计
     */
    @ApiModelProperty(value = "比例统计",name = "ratioCensus")
    private String ratioCensus;

    /**
     * 科研工资合计
     */
    @ApiModelProperty(value = "科研工资合计",name = "total")
    private BigDecimal total;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间",name = "declareTime")
    private Date declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",name = "remarks")
    private String remarks;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人",name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除：0：未删除；其他时间，删除
     */
    @ApiModelProperty(value = "是否删除：0：未删除；其他时间，删除",name = "isDeleted")
    private String isDeleted;

    }
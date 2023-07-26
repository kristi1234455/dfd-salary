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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("item")
@Accessors(chain = true)
public class Item implements Serializable {
    private static final long serialVersionUID = 6637495287436958527L;
    /**
     * 主键
     */
    @TableId(value="id",type = IdType.AUTO)
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 工程总称
     */
    @ApiModelProperty(value = "工程总称", name = "projectName")
    private String projectName;

    /**
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    private String itemNumber;

    /**
     * 计划号
     */
    @ApiModelProperty(value = "计划号", name = "planNumber")
    private String planNumber;
    /**
     * 合同子项号
     */
    @ApiModelProperty(value = "合同子项号", name = "subItemNumber")
    private String subItemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumber")
    private String virtualSubItemNumber;

    /**
     * 合同金额(万元）
     */
    @ApiModelProperty(value = "合同金额(万元）", name = "agreementMoney")
    private BigDecimal agreementMoney;

    /**
     * 预测合同金额(万元)
     */
    @ApiModelProperty(value = "预测合同金额(万元)", name = "forcastAgreementMoney")
    private BigDecimal forcastAgreementMoney;

    /**
     * 合同子项金额
     */
    @ApiModelProperty(value = "合同子项金额", name = "subItemMoney")
    private BigDecimal subItemMoney;

    /**
     * 项目属性，1、投标项目；2、EPC项目；3、科研项目
     */
    @ApiModelProperty(value = "项目属性，1、投标项目；2、EPC项目；3、科研项目", name = "itemProperties")
    private String itemProperties;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private String itemManager;

    /**
     * 项目地点
     */
    @ApiModelProperty(value = "项目地点", name = "itemLocation")
    private String itemLocation;

    /**
     * 项目概述
     */
    @ApiModelProperty(value = "项目概述", name = "itemSummary")
    private String itemSummary;

    /**
     * 项目开始时间
     */
    @ApiModelProperty(value = "项目开始时间", name = "itemStartTime")
    private Date itemStartTime;

    /**
     * 项目计划完成时间
     */
    @ApiModelProperty(value = "项目计划完成时间", name = "itemPlanFinishTime")
    private Date itemPlanFinishTime;

    /**
     * 项目阶段
     */
    @ApiModelProperty(value = "项目阶段", name = "itemStage")
    private Integer itemStage;

    /**
     * 商务经理
     */
    @ApiModelProperty(value = "商务经理", name = "businessManager")
    private String businessManager;

    /**
     * 投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private String bidDirector;

    /**
     * 投标开始时间
     */
    @ApiModelProperty(value = "投标开始时间", name = "bidStartTime")
    private Date bidStartTime;

    /**
     * 定标时间
     */
    @ApiModelProperty(value = "定标时间", name = "picketageTime")
    private Date picketageTime;

    /**
     * 设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private String designManager;

    /**
     * 科研经理
     */
    @ApiModelProperty(value = "科研经理", name = "scientificManager")
    private String scientificManager;

    /**
     * 技术管理费
     */
    @ApiModelProperty(value = "技术管理费", name = "technicalFee")
    private BigDecimal technicalFee;

    /**
     * 项目工资
     */
    @ApiModelProperty(value = "项目工资", name = "itemSalary")
    private BigDecimal itemSalary;

    /**
     * 项目绩效
     */
    @ApiModelProperty(value = "项目绩效", name = "itemPerformance")
    private BigDecimal itemPerformance;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designSalary")
    private BigDecimal designSalary;

    /**
     * 项目管理所
     */
    @ApiModelProperty(value = "项目管理所", name = "itemLeader")
    private String itemLeader;

    /**
     * 经营所
     */
    @ApiModelProperty(value = "经营所", name = "agencyLeader")
    private String agencyLeader;

    /**
     * 设计所
     */
    @ApiModelProperty(value = "设计所", name = "designLeader")
    private String designLeader;

    /**
     * 工程所
     */
    @ApiModelProperty(value = "工程所", name = "engineeringLeader")
    private String engineeringLeader;

    /**
     * 部门分管领导
     */
    @ApiModelProperty(value = "部门分管领导", name = "subLeader")
    private String subLeader;

    /**
     * 部门职能领导
     */
    @ApiModelProperty(value = "部门职能领导", name = "functionalLeader")
    private String functionalLeader;

    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人", name = "departmenLeader")
    private String departmenLeader;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除：0:未删除；其他时间，删除
     */
    @ApiModelProperty(value = "是否删除：0:未删除；其他时间，删除", name = "isDeleted")
    private String isDeleted;
}
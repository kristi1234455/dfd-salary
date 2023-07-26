package com.dfd.entity;

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
@TableName("item_total_salary")
@Accessors(chain = true)
public class ItemTotalSalary {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "room")
    private String room;

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
    @ApiModelProperty(value = "项目工资申报日期", name = "payday")
    private Date payday;

    /**
     * 项目工资申报金额（元）
     */
    @ApiModelProperty(value = "项目工资申报金额（元）", name = "salary")
    private BigDecimal salary;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "mark")
    private String mark;

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
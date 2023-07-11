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

@TableName("item_salary")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ItemSalary {
    /**
     * id
     */
    @TableId(value="id",type = IdType.AUTO)
    @ApiModelProperty(value = "id", name = "id")
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
     * 项目工资预算
     */
    @ApiModelProperty(value = "项目工资预算", name = "itemSalaryBudget")
    private BigDecimal itemSalaryBudget;

    /**
     * 部门批准项目工资
     */
    @ApiModelProperty(value = "部门批准项目工资", name = "approveItemFee")
    private BigDecimal approveItemFee;

    /**
     * 项目工资技术管理费额度
     */
    @ApiModelProperty(value = "项目工资技术管理费额度", name = "technologyManagerFee")
    private BigDecimal technologyManagerFee;

    /**
     * 岗位工资标准
     */
    @ApiModelProperty(value = "岗位工资标准", name = "postSalaryStandard")
    private BigDecimal postSalaryStandard;

    /**
     * 策划批准系数
     */
    @ApiModelProperty(value = "策划批准系数", name = "planApproveFactor")
    private String planApproveFactor;

    /**
     * 申报系数
     */
    @ApiModelProperty(value = "申报系数", name = "declareFactor")
    private String declareFactor;

    /**
     * 申报发放工资
     */
    @ApiModelProperty(value = "申报发放工资", name = "declareGrant")
    private BigDecimal declareGrant;

    /**
     * 项目阶段：1、设计阶段；2、采购阶段；3、制造阶段；4、安装调试阶段；5、安装验收阶段；6、终验收阶段；7、质保阶段；
     */
    @ApiModelProperty(value = "项目阶段：1、设计阶段；2、采购阶段；3、制造阶段；4、安装调试阶段；5、安装验收阶段；6、终验收阶段；7、质保阶段；", name = "itemStage")
    private String itemStage;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private String declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;

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
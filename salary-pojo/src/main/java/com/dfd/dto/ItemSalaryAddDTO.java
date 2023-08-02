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

/**
 * @author yy
 * @date 2023/6/12 14:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class ItemSalaryAddDTO implements Serializable {

    private static final long serialVersionUID = -3001008428348146056L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;
    /**
     * 项目人员表uid
     */
    @ApiModelProperty(value = "项目人员表uid", name = "itemMemberUid")
    @NotBlank(message = "项目人员表uid不能为空")
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
     * 岗位工资标准
     */
    @ApiModelProperty(value = "岗位工资标准", name = "postSalaryStandard")
    @NotBlank(message = "项目人员表uid不能为空")
    private String postSalaryStandard;

    /**
     * 策划批准系数
     */
    @ApiModelProperty(value = "策划批准系数", name = "planApproveFactor")
    @NotBlank(message = "策划批准系数不能为空")
    private String planApproveFactor;

    /**
     * 申报系数
     */
    @ApiModelProperty(value = "申报系数", name = "declareFactor")
    @NotBlank(message = "申报系数不能为空")
    private String declareFactor;

    /**
     * 申报发放工资
     */
    @ApiModelProperty(value = "申报发放工资", name = "declareGrant")
    private String declareGrant;

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
}

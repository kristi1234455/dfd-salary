package com.dfd.dto;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemDTO implements Serializable {
    private static final long serialVersionUID = 1535395190750036262L;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型", name = "itemProperties", required = true)
    @NotBlank(message = "项目类型不能为空")
    private String itemProperties;

    /**
     * 技术管理费
     */
    @ApiModelProperty(value = "技术管理费", name = "technologyManagerFee")
    private BigDecimal technologyManagerFee;

    /**
     * 项目工资
     */
    @ApiModelProperty(value = "项目工资", name = "itemSalary")
    private BigDecimal itemSalary;

    /**
     * 项目绩效
     */
    @ApiModelProperty(value = "项目绩效", name = "performanceSalary")
    private BigDecimal performanceSalary;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designSalary")
    private BigDecimal designSalary;

    /**
     * 设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private String designManager;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private String itemManager;

    /**
     * 阶段策划系数对象
     */
    @ApiModelProperty(value = "阶段策划系数对象", name = "planCoefficient")
    private List<PlanCoefficientDTO> planCoefficient;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    @ApiModelProperty(value = "部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；", name = "department")
    private JSONObject department;

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    @ApiModelProperty(value = "流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；", name = "manager")
    private JSONObject manager;

}
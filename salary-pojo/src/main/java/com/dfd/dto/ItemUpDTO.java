package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ItemUpDTO implements Serializable {
    private static final long serialVersionUID = 1535395190750036262L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型", name = "itemProperties")
    private String itemProperties;

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
     * 阶段策划系数对象
     */
    @ApiModelProperty(value = "阶段策划系数对象", name = "itemPlanDTOList")
    private List<ItemPlanDTO> itemPlanDTOList;


}
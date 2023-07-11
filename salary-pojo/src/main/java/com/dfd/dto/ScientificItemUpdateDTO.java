package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/7 15:52
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ScientificItemUpdateDTO implements Serializable {
    private static final long serialVersionUID = 2281771843044746872L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName", required = true)
    @NotBlank(message = "项目名称不能为空")
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
    private BigDecimal technicalFee;

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
     * 科研主持人
     */
    @ApiModelProperty(value = "科研主持人", name = "scientificManager")
    private String scientificManager;

    /**
     * 项目管理所
     */
    @ApiModelProperty(value = "项目管理所", name = "itemLeader")
    private String itemLeader;

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
     * 项目成员
     */
    @ApiModelProperty(value = "项目成员对象", name = "itemMemberDTOS")
    @NotNull(message = "项目成员对象不能为空")
    @Size(min=1,message = "项目成员对象长度最小为1")
    private List<ItemMemberDTO> itemMemberDTOS;
}

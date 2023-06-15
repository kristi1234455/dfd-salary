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

/**
 * @author yy
 * @date 2023/6/7 15:52
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "职能对象")
public class ScientificItemDTO implements Serializable {
    private static final long serialVersionUID = 2281771843044746872L;

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

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
     * 科研主持人
     */
    @ApiModelProperty(value = "科研主持人", name = "scientificManager")
    private String scientificManager;

    /**
     * 职能对象
     */
    @ApiModelProperty(value = "职能对象", name = "managerDTO")
    private List<ManagerDTO> managerDTO;
}

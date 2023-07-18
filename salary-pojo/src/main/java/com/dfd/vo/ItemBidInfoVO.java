package com.dfd.vo;

import com.dfd.dto.ItemMemberDTO;
import com.dfd.dto.ItemPlanDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yy
 * @date 2023/3/31 17:20
 */
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class ItemBidInfoVO implements Serializable {

    private static final long serialVersionUID = 247833528975138822L;
    /**
     * uid
     */
    @ApiModelProperty(value = "项目uid", name = "uid")
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
     * 投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private String bidDirector;

    /**
     * 项目成员对象
     */
    @ApiModelProperty(value = "项目成员对象", name = "itemPlanDTOList")
    @NotNull(message = "项目成员对象不能为空")
    @Size(min=1,message = "项目成员对象长度最小为1")
    private List<ItemMemberDTO> itemPlanDTOList;

    /**
     * 项目管理所
     */
    @ApiModelProperty(value = "项目管理所", name = "itemLeader")
    @NotBlank(message = "项目管理所不能为空")
    private String itemLeader;

    /**
     * 部门分管领导
     */
    @ApiModelProperty(value = "部门分管领导", name = "subLeader")
    @NotBlank(message = "部门分管领导不能为空")
    private String subLeader;

    /**
     * 部门职能领导
     */
    @ApiModelProperty(value = "部门职能领导", name = "functionalLeader")
    @NotBlank(message = "部门职能领导不能为空")
    private String functionalLeader;

    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人", name = "departmenLeader")
    @NotBlank(message = "部门负责人不能为空")
    private String departmenLeader;
}

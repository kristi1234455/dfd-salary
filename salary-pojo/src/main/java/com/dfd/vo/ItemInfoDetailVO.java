package com.dfd.vo;

import com.dfd.dto.ItemMemberDTO;
import com.dfd.dto.ItemPlanDTO;
import com.dfd.entity.ItemPlan;
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
public class ItemInfoDetailVO implements Serializable {

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
    private BigDecimal itemPerformance;

    /**
     * 设计工资
     */
    @ApiModelProperty(value = "设计工资", name = "designSalary")
    private BigDecimal designSalary;

    /**
     * 设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private MemberVO designManager;

    /**
     * 投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private MemberVO bidDirector;

    /**
     * 科研主持人
     */
    @ApiModelProperty(value = "科研主持人", name = "scientificManager")
    private MemberVO scientificManager;

    /**
     * 项目成员对象
     */
    @ApiModelProperty(value = "项目成员对象", name = "itemPlanDTOList")
    @NotNull(message = "项目成员对象不能为空")
    @Size(min=1,message = "项目成员对象长度最小为1")
    private List<ItemMemberDTO> itemMemberDTOS;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private MemberVO itemManager;


    /**
     * 阶段策划系数对象
     */
    @ApiModelProperty(value = "阶段策划系数对象", name = "itemPlanDTOList")
    @NotNull(message = "阶段策划系数对象不能为空")
    @Size(min=1,message = "阶段策划系数对象长度最小为1")
    private List<ItemPlanInfoVO> itemPlanDTOList;

    /**
     * 项目管理所
     */
    @ApiModelProperty(value = "项目管理所", name = "itemLeader")
    @NotBlank(message = "项目管理所不能为空")
    private MemberVO itemLeader;

    /**
     * 经营所
     */
    @ApiModelProperty(value = "经营所", name = "agencyLeader")
    @NotBlank(message = "经营所不能为空")
    private MemberVO agencyLeader;

    /**
     * 设计所
     */

    @ApiModelProperty(value = "设计所", name = "designLeader")
    @NotBlank(message = "设计所不能为空")
    private MemberVO designLeader;

    /**
     * 工程所
     */
    @ApiModelProperty(value = "工程所", name = "engineeringLeader")
    @NotBlank(message = "工程所不能为空")
    private MemberVO engineeringLeader;

    /**
     * 部门分管领导
     */
    @ApiModelProperty(value = "部门分管领导", name = "subLeader")
    @NotBlank(message = "部门分管领导不能为空")
    private MemberVO subLeader;

    /**
     * 部门职能领导
     */
    @ApiModelProperty(value = "部门职能领导", name = "functionalLeader")
    @NotBlank(message = "部门职能领导不能为空")
    private MemberVO functionalLeader;

    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人", name = "departmenLeader")
    @NotBlank(message = "部门负责人不能为空")
    private MemberVO departmenLeader;
}

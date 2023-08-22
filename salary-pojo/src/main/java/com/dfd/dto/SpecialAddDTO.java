package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/12 8:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SpecialAddDTO extends Query implements Serializable {

    private static final long serialVersionUID = 2049639490366198003L;

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目人员uid
     */
    @ApiModelProperty(value = "项目人员uid",name = "itemMemberUid",required = true)
    @NotBlank(message = "项目人员uid不能为空")
    private String itemMemberUid;

    /**
     * 所室
     */
    @ApiModelProperty(value = "所室", name = "department")
    private String department;

    /**
     * 专岗基础工资
     */
    @ApiModelProperty(value = "专岗基础工资", name = "specialBaseFee")
    private String specialBaseFee;

    /**
     * 专岗工资系数最大值
     */
    @ApiModelProperty(value = "专岗工资系数最大值", name = "specialMaxCoefficient")
    private String specialMaxCoefficient;

    /**
     * 专岗津贴标准工资
     */
    @ApiModelProperty(value = "专岗津贴标准工资", name = "standardSubsidy")
    private String standardSubsidy;

    /**
     * 当月专岗津贴系数
     */
    @ApiModelProperty(value = "当月专岗津贴系数", name = "subsidyCoefficient")
    private String subsidyCoefficient;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
    /**
     * 专岗申报时间
     */
    @ApiModelProperty(value = "专岗申报时间", name = "specialDeclareTime")
    private String specialDeclareTime;
}

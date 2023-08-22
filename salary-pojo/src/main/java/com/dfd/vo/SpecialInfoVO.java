package com.dfd.vo;

import com.dfd.utils.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/12 8:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class SpecialInfoVO extends Query implements Serializable {
    private static final long serialVersionUID = 2812845239417651576L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 项目成员uid
     */
    @ApiModelProperty(value = "项目成员uid", name = "itemMemberUid")
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
     * 所室
     */
    @ApiModelProperty(value = "所室", name = "department")
    private String department;

    /**
     * 专岗申报时间
     */
    @ApiModelProperty(value = "专岗申报时间", name = "specialDeclareTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String specialDeclareTime;

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
}

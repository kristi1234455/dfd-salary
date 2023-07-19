package com.dfd.dto;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class CheckListPartSubmitDTO implements Serializable {

    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * 项目表uid
     */
    @ApiModelProperty(value = "项目表uid", name = "itemUid")
    @NotBlank(message = "项目表uid不能为空")
    private String itemUid;

    /**
     * 审核人uid
     */
    @ApiModelProperty(value = "审核人uid", name = "auditorUid")
    private String auditorUid;

    /**
     * 审核人名字
     */
    @ApiModelProperty(value = "审核人名字", name = "auditorName")
    private String auditorName;

    /**
     * 审核任务状态：0，未审核，1，审核通过，2，不通过
     */
    @ApiModelProperty(value = "审核任务状态：0，未审核，1，审核通过，2，不通过", name = "taskStatus")
    private Integer taskStatus;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见", name = "taskOpinion")
    private String taskOpinion;


}

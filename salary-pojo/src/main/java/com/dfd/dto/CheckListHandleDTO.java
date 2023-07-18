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
public class CheckListHandleDTO implements Serializable {

    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * 审核数据uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人", name = "auditor")
    @NotBlank(message = "审核人uid不能为空")
    private String auditor;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间", name = "auditTime")
    private Date auditTime;

    /**
     * 审核任务状态：0，未审核，1，审核通过，2，不通过
     */
    @ApiModelProperty(value = "审核任务状态：0，未审核，1，审核通过，2，不通过", name = "taskStatus")
    @NotBlank(message = "审核任务状态不能为空")
    private Integer taskStatus;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见", name = "taskOpinion")
    @NotBlank(message = "审核意见不能为空")
    private String taskOpinion;
}

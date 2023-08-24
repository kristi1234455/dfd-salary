package com.dfd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class CheckListPartInfoVO implements Serializable {
    private static final long serialVersionUID = -7079940868863860032L;

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
     * 审核人工号
     */
    @ApiModelProperty(value = "审核人工号", name = "auditorNumber")
    private String auditorNumber;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间", name = "auditTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date auditTime;

    /**
     * 审核任务名字
     */
    @ApiModelProperty(value = "审核任务名字", name = "taskName")
    private String taskName;

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

    /**
     * 流程序号
     */
    @ApiModelProperty(value = "流程序号", name = "taskSequenceNumber")
    private Integer taskSequenceNumber;

    /**
     * 审核人级别
     */
    @ApiModelProperty(value = "审核人级别", name = "auditorLevel")
    private String auditorLevel;
}

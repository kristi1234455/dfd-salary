package com.dfd.vo;

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
public class CheckListVO implements Serializable {
    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目表uid
     */
    @ApiModelProperty(value = "项目表uid", name = "itemUid")
    private String itemUid;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人", name = "auditor")
    private String auditor;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间", name = "auditTime")
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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;
}

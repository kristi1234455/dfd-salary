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
public class CheckListNormalVO implements Serializable {
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

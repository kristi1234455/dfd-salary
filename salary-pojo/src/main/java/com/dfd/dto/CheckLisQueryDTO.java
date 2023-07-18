package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class CheckLisQueryDTO extends Query implements Serializable {
    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * 项目表uid
     */
    @ApiModelProperty(value = "项目表uid", name = "itemUid")
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 审核任务名字
     */
    @ApiModelProperty(value = "审核任务名字", name = "taskName")
    private String taskName;
}

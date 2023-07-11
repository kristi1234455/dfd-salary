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
 * @date 2023/6/9 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class DesignSalaryInfoDTO extends Query implements Serializable {

    private static final long serialVersionUID = -7232416727000836006L;
    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间",name = "declareTime")
    private String declareTime;
}

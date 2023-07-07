package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 14:19
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemPlanDelDTO implements Serializable {


    private static final long serialVersionUID = 857364405437473335L;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid", required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;

}

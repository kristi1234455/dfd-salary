package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 15:52
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemScientificInfoDTO implements Serializable {

    private static final long serialVersionUID = -6798444832590243255L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

}

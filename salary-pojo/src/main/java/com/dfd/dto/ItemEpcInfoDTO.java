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

/**
 * @author yy
 * @date 2023/6/7 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class ItemEpcInfoDTO implements Serializable {

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid",required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;
}

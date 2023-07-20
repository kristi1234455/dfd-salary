package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/7 15:52
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class ItemBidInfoDTO implements Serializable {

    private static final long serialVersionUID = -6798444832590243255L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid", required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;

}

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
public class ItemInfoDetailDTO implements Serializable {

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid",required = true)
    @NotBlank(message = "uid不能为空")
    private String uid;

    /**
     * 项目属性，1、投标项目；2、EPC项目；3、科研项目；4、设计项目
     */
    @ApiModelProperty(value = "项目属性，1、投标项目；2、EPC项目；3、科研项目；4、设计项目", name = "itemProperties")
    @NotBlank(message = "项目属性不能为空")
    private String itemProperties;
}

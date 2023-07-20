package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class CheckListNormalDTO implements Serializable {

    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * 项目表uid
     */
    @ApiModelProperty(value = "项目表uid", name = "itemUid")
    @NotBlank(message = "项目表uid不能为空")
    private String itemUid;
}
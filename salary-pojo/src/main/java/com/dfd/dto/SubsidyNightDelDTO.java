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
import java.util.List;

/**
 * @author yy
 * @date 2023/6/14 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SubsidyNightDelDTO implements Serializable {
    private static final long serialVersionUID = 1344513368195453557L;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uids", required = true)
    @NotNull(message = "uids不能为空")
    @Size(min = 1, message = "uids不能为空")
    private List<String> uids;
}

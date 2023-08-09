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
 * @date 2023/3/31 16:53
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDeleteDTO implements Serializable {

    private static final long serialVersionUID = 649145916500915762L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uids", required = true)
    @NotNull(message = "uids不能为空")
    @Size(min = 1, message = "uids不能为空")
    private List<String> uids;


}

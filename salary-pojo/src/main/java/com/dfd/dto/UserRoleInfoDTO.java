package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/3/31 16:53
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleInfoDTO extends Query implements Serializable {

    private static final long serialVersionUID = 649145916500915762L;

    @ApiModelProperty(value = "工号", name = "number", example = "13412345667")
    private String number;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "nickname")
    private String nickname;
}

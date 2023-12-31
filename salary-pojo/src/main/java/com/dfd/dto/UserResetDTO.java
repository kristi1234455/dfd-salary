package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/3/31 16:53
 */
@ApiModel(value = "密码重置对象", description = "从客户端，由用户传入的数据封装在此entity中")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResetDTO implements Serializable {

    private static final long serialVersionUID = 649145916500915762L;
    @ApiModelProperty(value = "手机号", name = "phone", example = "13412345667", required = false)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(message = "密码不能少于{min}个字符", min = 6)
    private String password;
}

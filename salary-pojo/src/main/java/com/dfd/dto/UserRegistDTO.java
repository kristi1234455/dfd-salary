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

@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistDTO implements Serializable {

    private static final long serialVersionUID = 632859386921414554L;
    @ApiModelProperty(value = "用户名", name = "username", example = "dfd")
    private String username;

    @ApiModelProperty(value = "手机号", name = "phone", example = "13412345667", required = true)
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    @NotBlank(message = "密码不能为空")
    @Length(message = "密码不能少于{min}个字符", min = 6)
    private String password;

    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = true)
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}

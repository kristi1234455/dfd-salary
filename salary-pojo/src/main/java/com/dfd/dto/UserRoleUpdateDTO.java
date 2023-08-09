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
 * @date 2023/3/31 16:53
 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 649145916500915762L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    @NotBlank(message = "项目uid不能为空")
    private String uid;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号",name = "number")
    private String number;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "nickname")
    private String nickname;

    /**
     * 权限：1、项目经理；2、所长；3、部门分管领导；4、部门职能领导；5、部门负责人；6：管理员
     */
    @ApiModelProperty(value = "权限：1、项目经理；2、所长；3、部门分管领导；4、部门职能领导；5、部门负责人；6：管理员", name = "role")
    private String role;


}

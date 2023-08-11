package com.dfd.vo;

import com.dfd.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
@Accessors(chain = true)
public class UserRoleVO implements Serializable {
    private static final long serialVersionUID = 1633646096188572006L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
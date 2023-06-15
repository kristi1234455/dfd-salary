package com.dfd.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "用户信息对象")
public class User implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Long id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", name = "userName")
    private String userName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", name = "nickname")
    private String nickname;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", name = "password")
    private String password;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", name = "pic")
    private String pic;

    /**
     * 权限：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；5：管理员
     */
    @ApiModelProperty(value = "权限：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；5：管理员", name = "role")
    private String role;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除，0，未删除；其他：删除
     */
    private String isDeleted;
}
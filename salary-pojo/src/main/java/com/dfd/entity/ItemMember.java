package com.dfd.entity;

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
public class ItemMember implements Serializable {
    private static final long serialVersionUID = -3389887143839160084L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",name = "id")
    private Long id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid",name = "uid")
    private String uid;

    /**
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid",name = "itemUid")
    private String itemUid;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号",name = "number")
    private String number;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字",name = "name")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别",name = "gender")
    private Integer gender;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    @ApiModelProperty(value = "部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；",name = "department")
    private String department;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级",name = "rank")
    private String rank;

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    @ApiModelProperty(value = "流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；",name = "manager")
    private String manager;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务",name = "post")
    private String post;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业",name = "major")
    private String major;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间",name = "declareTime")
    private Date declareTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",name = "remarks")
    private String remarks;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人",name = "createdBy")
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",name = "createdTime")
    private Date createdTime;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人",name = "updatedBy")
    private String updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",name = "updatedTime")
    private Date updatedTime;

    /**
     * 是否删除，0，未删除；其他：删除
     */
    @ApiModelProperty(value = "是否删除，0，未删除；其他：删除",name = "isDeleted")
    private String isDeleted;

}
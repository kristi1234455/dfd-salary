package com.dfd.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class Member {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number")
    private String number;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", name = "name")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", name = "gender")
    private Integer gender;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    @ApiModelProperty(value = "部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；", name = "department")
    private String department;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级", name = "ranks")
    private String ranks;

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    @ApiModelProperty(value = "流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；", name = "manager")
    private String manager;

    /**
     * 职务
     */
    @ApiModelProperty(value = "职务", name = "post")
    private String post;

    /**
     * 专业
     */
    @ApiModelProperty(value = "专业", name = "major")
    private String major;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;

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
     * 是否删除：0：未删除，其他时间：删除
     */
    @ApiModelProperty(value = "是否删除：0：未删除，其他时间：删除", name = "isDeleted")
    private String isDeleted;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * uid
     * @return uid uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * uid
     * @param uid uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 工号
     * @return number 工号
     */
    public String getNumber() {
        return number;
    }

    /**
     * 工号
     * @param number 工号
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * 名字
     * @return name 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 名字
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 性别
     * @return gender 性别
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 性别
     * @param gender 性别
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     * @return department 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    public String getDepartment() {
        return department;
    }

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     * @param department 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    /**
     * 职级
     * @return ranks 职级
     */
    public String getRanks() {
        return ranks;
    }

    /**
     * 职级
     * @param ranks 职级
     */
    public void setRanks(String ranks) {
        this.ranks = ranks == null ? null : ranks.trim();
    }

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     * @return manager 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    public String getManager() {
        return manager;
    }

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     * @param manager 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    /**
     * 职务
     * @return post 职务
     */
    public String getPost() {
        return post;
    }

    /**
     * 职务
     * @param post 职务
     */
    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    /**
     * 专业
     * @return major 专业
     */
    public String getMajor() {
        return major;
    }

    /**
     * 专业
     * @param major 专业
     */
    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    /**
     * 备注
     * @return remarks 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * 创建人
     * @return created_by 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 创建人
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    /**
     * 创建时间
     * @return created_time 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新人
     * @return updated_by 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 更新人
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    /**
     * 更新时间
     * @return updated_time 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 更新时间
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 是否删除：0：未删除，其他时间：删除
     * @return is_deleted 是否删除：0：未删除，其他时间：删除
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除：0：未删除，其他时间：删除
     * @param isDeleted 是否删除：0：未删除，其他时间：删除
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}
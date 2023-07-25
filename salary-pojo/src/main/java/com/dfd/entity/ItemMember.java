package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("item_member")
public class ItemMember {
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
     * 项目信息表uid
     */
    @ApiModelProperty(value = "项目信息表uid", name = "itemUid")
    private String itemUid;

    /**
     * 基本信息表uid
     */
    @ApiModelProperty(value = "基本信息表uid", name = "memberUid")
    private String memberUid;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private Date declareTime;

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



}
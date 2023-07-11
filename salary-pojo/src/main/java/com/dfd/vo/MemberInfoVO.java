package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 16:28
 */
@ApiModel
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MemberInfoVO implements Serializable {

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目人员uid
     */
    @ApiModelProperty(value = "项目人员uid", name = "itemMemberUid")
    private String itemMemberUid;

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
     * 职级
     */
    @ApiModelProperty(value = "职级", name = "ranks")
    private String ranks;

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
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

}

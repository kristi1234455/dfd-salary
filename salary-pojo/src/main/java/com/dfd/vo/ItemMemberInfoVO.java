package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/7 16:28
 */
@ApiModel(value = "项目人员信息对象")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemMemberInfoVO implements Serializable {

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
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    @ApiModelProperty(value = "工号", name = "number")
    private String department;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级", name = "rank")
    private String rank;

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
    @ApiModelProperty(value = "备注", name = "remark")
    private String remark;

}

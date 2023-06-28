package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/8 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class ItemMemberInfoDTO implements Serializable {

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 月份
     */
    @ApiModelProperty(value = "月份", name = "month", required = true)
    @NotBlank(message = "月份不能为空")
    private Date month;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number", required = true)
    @NotBlank(message = "工号不能为空")
    private String number;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", name = "name", required = true)
    @NotBlank(message = "名字不能为空")
    private String name;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；
     */
    @ApiModelProperty(value = "部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；", name = "department", required = true)
    @NotBlank(message = "部门所室不能为空")
    private String department;

    /**
     * 职级
     */
    @ApiModelProperty(value = "职级", name = "ranks")
    private String ranks;

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    @ApiModelProperty(value = "流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；", name = "manager", required = true)
    @NotBlank(message = "流程节点不能为空")
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

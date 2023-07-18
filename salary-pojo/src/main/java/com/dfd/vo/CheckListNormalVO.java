package com.dfd.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class CheckListNormalVO implements Serializable {
    private static final long serialVersionUID = -7079940868863860032L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 提交人
     */
    @ApiModelProperty(value = "提交人", name = "submitter")
    private String submitter;


    /**
     * 所长
     */
    @ApiModelProperty(value = "所长", name = "leader")
    private String leader;




    /**
     * 项目管理所
     */
    @ApiModelProperty(value = "项目管理所", name = "itemLeader")
    private String itemLeader;

    /**
     * 经营所
     */
    @ApiModelProperty(value = "经营所", name = "agencyLeader")
    private String agencyLeader;

    /**
     * 设计所
     */
    @ApiModelProperty(value = "设计所", name = "designLeader")
    private String designLeader;

    /**
     * 工程所
     */
    @ApiModelProperty(value = "工程所", name = "engineeringLeader")
    private String engineeringLeader;














    /**
     * 部门分管领导
     */
    @ApiModelProperty(value = "部门分管领导", name = "subLeader")
    private String subLeader;

    /**
     * 部门职能领导
     */
    @ApiModelProperty(value = "部门职能领导", name = "functionalLeader")
    private String functionalLeader;

    /**
     * 部门负责人
     */
    @ApiModelProperty(value = "部门负责人", name = "departmenLeader")
    private String departmenLeader;
}

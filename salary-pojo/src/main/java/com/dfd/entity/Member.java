package com.dfd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@TableName("member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Member {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    @TableId(value = "id", type = IdType.AUTO)
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
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "room")
    private String room;

    /**
     * 岗位名称:1、总承包管理；2、总承包管理、工艺设计；3、非标设计、机械化；4、非标机械设计；5、自动化设计；6、技术档案资料；7、技术档案资料
     */
    @ApiModelProperty(value = "岗位名称:1、总承包管理；2、总承包管理、工艺设计；3、非标设计、机械化；4、非标机械设计；5、自动化设计；6、技术档案资料；7、技术档案资料", name = "postTitle")
    private String postTitle;

    /**
     * 部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；5、主师室；6、设计所所长；7、工程所所长；8、项目管理所所长；9、总工程师；10、助理总经理；11、部门副总经理；12、部门总经理
     */
    @ApiModelProperty(value = "部门所室：1、项目管理所；2、经营所；3、设计所；4、工程所；5、主师室；6、设计所所长；7、工程所所长；8、项目管理所所长；9、总工程师；10、助理总经理；11、部门副总经理；12、部门总经理 ", name = "department")
    private String department;

    /**
     * 职称：1、高级工程师；2、工程师；3、馆员；4、技术员；5、研究员级高级工程师；6、助理工程师
     */
    @ApiModelProperty(value = "职称：1、高级工程师；2、工程师；3、馆员；4、技术员；5、研究员级高级工程师；6、助理工程师", name = "post")
    private String post;

    /**
     * 技术职务：1、见习生；2、设计经理、审核人；3、设计经理、专业负责人；4、设计人；5、审核人；6、项目管理；7、项目经理、审核人；8、校对人；9、专业负责人；10、专业工程师；11、专业经理
     */
    @ApiModelProperty(value = "技术职务：1、见习生；2、设计经理、审核人；3、设计经理、专业负责人；4、设计人；5、审核人；6、项目管理；7、项目经理、审核人；8、校对人；9、专业负责人；10、专业工程师；11、专业经理", name = "technicalPosition")
    private String technicalPosition;

    /**
     * 职级：1、助理工程师；2、二级工程师；3、一级工程师；4、主任工程师；5、实习设计师；6、助理设计师；7、二级设计师；8、一级设计师；9、主任设计师；10、见习本科；11、助理管理师；12、技术管理；
     */
    @ApiModelProperty(value = "职级：1、助理工程师；2、二级工程师；3、一级工程师；4、主任工程师；5、实习设计师；6、助理设计师；7、二级设计师；8、一级设计师；9、主任设计师；10、见习本科；11、助理管理师；12、技术管理；", name = "ranks")
    private String ranks;

    /**
     * 流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；
     */
    @ApiModelProperty(value = "流程节点：1、项目经理；2、部门分管领导；3、部门职能领导；4、部门负责人；", name = "manager")
    private String manager;

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


}
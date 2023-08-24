package com.dfd.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class TotalSalarySumItemInfoVO implements Serializable {

    private static final long serialVersionUID = -1325759829897239212L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * 立项号
     */
    @ApiModelProperty(value = "立项号", name = "itemNumber")
    private String itemNumber;

    /**
     * 虚拟合同子项号
     */
    @ApiModelProperty(value = "虚拟合同子项号", name = "virtualSubItemNumber")
    private String virtualSubItemNumber;

    /**
     * 项目经理
     */
    @ApiModelProperty(value = "项目经理", name = "itemManager")
    private String itemManager;

    /**
     * 投标经理
     */
    @ApiModelProperty(value = "投标经理", name = "bidDirector")
    private String bidDirector;

    /**
     * 设计经理
     */
    @ApiModelProperty(value = "设计经理", name = "designManager")
    private String designManager;

    /**
     * 科研经理
     */
    @ApiModelProperty(value = "科研经理", name = "scientificManager")
    private String scientificManager;

    /**
     * 项目属性，1、投标项目；2、EPC项目；3、科研项目；4、设计项目
     */
    @ApiModelProperty(value = "项目属性，1、投标项目；2、EPC项目；3、科研项目；4、设计项目", name = "itemProperties")
    private String itemProperties;

}
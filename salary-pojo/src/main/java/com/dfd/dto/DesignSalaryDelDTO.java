package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/9 15:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "封装设计工资对象",description = "用于封装设计工资对象")
public class DesignSalaryDelDTO implements Serializable {

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;


    /**
     * 项目人员ids
     */
    @ApiModelProperty(value = "项目人员ids",name = "itemMemberIds", required = true)
    @NotNull(message = "项目人员ids不能为空")
    @Size(min = 1, message = "项目人员ids不能为空")
    private List<String> itemMemberIds;

}

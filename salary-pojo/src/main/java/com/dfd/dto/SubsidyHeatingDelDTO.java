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
 * @date 2023/6/14 14:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "补助信息删除对象",description = "封装补助信息删除对象")
public class SubsidyHeatingDelDTO implements Serializable {

    private static final long serialVersionUID = 7367110015440319030L;
    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid",name = "itemUid",required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目人员ids
     */
    @ApiModelProperty(value = "项目人员ids",name = "itemMemberIds")
    @NotNull(message = "项目人员ids不能为空")
    @Size(min = 1, message = "项目人员ids不能为空")
    private List<String> itemMemberIds;
}

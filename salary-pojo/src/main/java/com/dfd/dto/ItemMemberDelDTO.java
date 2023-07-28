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
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 17:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class ItemMemberDelDTO {

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
     * 项目人员uids
     */
    @ApiModelProperty(value = "项目人员uids", name = "itemMemberUids", required = true)
    @NotNull(message = "项目人员uids不能为空")
    @Size(min = 1, message = "项目人员uids不能为空")
    private List<String> itemMemberUids;
}

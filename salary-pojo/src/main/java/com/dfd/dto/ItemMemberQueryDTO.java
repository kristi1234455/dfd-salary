package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/8 16:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class ItemMemberQueryDTO extends Query implements Serializable {

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

}

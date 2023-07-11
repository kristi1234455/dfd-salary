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
 * @date 2023/6/12 8:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SpecialAddDTO extends Query implements Serializable {

    private static final long serialVersionUID = 2049639490366198003L;

    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 申报时间
     */
    @ApiModelProperty(value = "申报时间", name = "declareTime")
    private String declareTime;
}

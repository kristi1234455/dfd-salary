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
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/14 11:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class SubsidyNightInfoDTO extends Query implements Serializable {
    private static final long serialVersionUID = -4860944566554762548L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid",required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;
    /**
     * 夜班申报时间
     */
    @ApiModelProperty(value = "夜班申报时间", name = "nightDeclareTime")
    private Date nightDeclareTime;
}

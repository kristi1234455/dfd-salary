package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/12 16:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "考勤信息对象", description = "封装考勤信息对象")
public class AttendanceMonInfoDTO extends Query implements Serializable {
    private static final long serialVersionUID = 6111805323634341948L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 年
     */
    @ApiModelProperty(value = "年", name = "year", required = true)
    @NotEmpty(message = "年不能为空")
    private Integer year;

    /**
     * 月
     */
    @ApiModelProperty(value = "月", name = "month", required = true)
    @NotEmpty(message = "月不能为空")
    private Integer month;
}

package com.dfd.dto;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/9 11:53
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "职能对象")
public class ScientificSalaryInfoDTO extends Query implements Serializable {

    private static final long serialVersionUID = 4698006363524509510L;
    /**
     * 申报月份
     */
    @ApiModelProperty(value = "申报月份",name = "declareTime")
    private Date declareTime;
}

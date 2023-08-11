package com.dfd.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author yy
 * @date 2023/6/7 17:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
@Component
public class Query {

    /**
     * 当前页数
     */
    @ApiModelProperty(value = "当前页数", name = "currentPage")
    Integer currentPage = 1;

    /**
     * 当前页面数据条数
     */
    @ApiModelProperty(value = "当前页面数据条数", name = "pageSize")
    Integer pageSize = 10;

}

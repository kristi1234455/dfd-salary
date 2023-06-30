package com.dfd.dto;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/9 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class ScientificSalaryDelDTO implements Serializable {

    private static final long serialVersionUID = 6773147183783657333L;

    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uids", required = true)
    @NotNull(message = "uids不能为空")
    @Size(min = 1, message = "uids不能为空")
    private List<String> uids;
}

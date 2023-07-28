package com.dfd.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MemberDelDTO implements Serializable {

    /**
     * 项目人员uids
     */
    @ApiModelProperty(value = "项目人员uids", name = "uids", required = true)
    @NotNull(message = "项目人员uids不能为空")
    @Size(min = 1, message = "项目人员uids不能为空")
    private List<String> uids;


}
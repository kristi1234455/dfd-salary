package com.dfd.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/8 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class ItemMemberDTO implements Serializable {

    /**
     * 项目人员主键
     */
    @ApiModelProperty(value = "项目人员主键",name = "id")
    @NotNull(message = "项目人员主键不能为空")
    private String memberUid;


}

package com.dfd.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class ItemInfoQueryDTO extends Query implements Serializable {

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName",required = false)
    private String itemName;

    /**
     * 项目属性，1、投标项目；2、EPC项目；3、科研项目
     */
    @ApiModelProperty(value = "项目属性，1、投标项目；2、EPC项目；3、科研项目", name = "itemProperties")
    private String itemProperties;

}

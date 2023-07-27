package com.dfd.vo;

import com.dfd.utils.Query;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yy
 * @date 2023/6/12 8:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class TechnicalFeeInfoVO implements Serializable {
    private static final long serialVersionUID = 2812845239417651576L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;


    /**
     * 技术管理费总费用
     */
    @ApiModelProperty(value = "技术管理费总费用", name = "totalTechnicalFee")
    private BigDecimal totalTechnicalFee;

    /**
     * 技术管理费已用费用
     */
    @ApiModelProperty(value = "技术管理费已用费用", name = "usedTechnicalFee")
    private BigDecimal usedTechnicalFee;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;
}

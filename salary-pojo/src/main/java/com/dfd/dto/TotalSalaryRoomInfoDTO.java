package com.dfd.dto;

import com.dfd.utils.DateUtil;
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
 * @date 2023/6/7 15:52
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
public class TotalSalaryRoomInfoDTO extends Query implements Serializable {
    private static final long serialVersionUID = 2281771843044746872L;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门", name = "room")
    private String room;

    /**
     * 项目工资申报日期
     */
    @ApiModelProperty(value = "项目工资申报日期", name = "declareTime")
    private String declareTime = DateUtil.getYM();

}

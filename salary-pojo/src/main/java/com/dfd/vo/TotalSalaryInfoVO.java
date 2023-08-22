package com.dfd.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class TotalSalaryInfoVO implements Serializable {

    private static final long serialVersionUID = -1325759829897239212L;

    /**
     * 当月工资汇总数据对象
     */
    @ApiModelProperty(value = "当月工资汇总数据对象", name = "totalSalaryBasicInfoVO")
    private TotalSalaryBasicInfoVO totalSalaryBasicInfoVO;

    /**
     * 当月项目工资汇总数据对象
     */
    @ApiModelProperty(value = "当月项目工资汇总数据对象", name = "totalSalaryItemInfoVOList")
    private List<TotalSalaryItemInfoVO> totalSalaryItemInfoVOList;
}
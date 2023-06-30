package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class AttendanceDTO implements Serializable {

    private static final long serialVersionUID = 5973207165741597324L;

    /**
     * 项目uid
     */
    @ApiModelProperty(value = "项目uid", name = "itemUid", required = true)
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * 项目人员Uid
     */
    @ApiModelProperty(value = "项目人员Uid", name = "itemMemberUid", required = true)
    @NotBlank(message = "项目人员Uid不能为空")
    private String itemMemberUid;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number", required = true)
    @NotBlank(message = "工号不能为空")
    private String number;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", name = "name", required = true)
    @NotBlank(message = "名字不能为空")
    private String name;

    /**
     * 年
     */
    @ApiModelProperty(value = "年", name = "year", required = true)
    @NotNull(message = "年不能为空")
    private Integer year;

    /**
     * 月
     */
    @ApiModelProperty(value = "月", name = "month", required = true)
    @NotNull(message = "月不能为空")
    private Integer month;

    /**
     * 考勤数据对象
     */
    @ApiModelProperty(value = "考勤数据对象", name = "attendanceDataDTOList", required = true)
    @NotNull(message = "考勤数据对象不能为空")
    private List<AttendanceDataDTO> attendanceDataDTOList;

    /**
     * 驻外天数
     */
    @ApiModelProperty(value = "驻外天数", name = "outgoingTotalDays")
    private Integer outgoingTotalDays;

    /**
     * 出勤天数
     */
    @ApiModelProperty(value = "出勤天数", name = "dutyTotalDays")
    private Integer dutyTotalDays;
}

package com.dfd.dto;

import com.alibaba.fastjson.JSONObject;
import com.dfd.vo.AttendanceMonDataVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yy
 * @date 2023/6/12 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class AttendanceDelDTO implements Serializable {

    private static final long serialVersionUID = -3770206178853823870L;

//    private List<Attenda>
    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid")
    @NotBlank(message = "项目uid不能为空")
    private String itemUid;

    /**
     * itemMemberUid
     */
    @ApiModelProperty(value = "itemMemberUid", name = "itemMemberUid")
    @NotBlank(message = "itemMemberUid不能为空")
    private String itemMemberUid;

    /**
     * 考勤对象
     */
    @ApiModelProperty(value = "考勤对象", name = "attendanceMonDataVOList")
    @NotNull(message = "考勤对象不能为空")
    @Size(min = 1, message = "考勤对象不能为空")
    private List<AttendanceMonDataVO> attendanceMonDataVOList;

}

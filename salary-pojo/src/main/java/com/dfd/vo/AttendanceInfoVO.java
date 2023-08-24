package com.dfd.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/12 16:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class AttendanceInfoVO implements Serializable {

    private static final long serialVersionUID = -8119785685718706166L;
//    /**
//     * 主键
//     */
//    @ApiModelProperty(value = "主键", name = "id")
//    private Integer id;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;
    /**
     * itemUid
     */
    @ApiModelProperty(value = "itemUid", name = "itemUid")
    private String itemUid;

    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称", name = "itemName")
    private String itemName;

    /**
     * itemMemberUid
     */
    @ApiModelProperty(value = "itemMemberUid", name = "itemMemberUid")
    private String itemMemberUid;

    /**
     * 工号
     */
    @ApiModelProperty(value = "工号", name = "number")
    private String number;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字", name = "name")
    private String name;

    /**
     * 年
     */
    @ApiModelProperty(value = "年", name = "year")
    private Integer year;

    /**
     * 月
     */
    @ApiModelProperty(value = "月", name = "month")
    private Integer month;

    /**
     * 日
     */
    @ApiModelProperty(value = "日", name = "day")
    private Integer day;

    /**
     * 考勤状态 1驻现场；2项目出差；3本地办公；4休假；
     */
    @ApiModelProperty(value = "考勤状态 1驻现场；2项目出差；3本地办公；4休假；", name = "status")
    private Integer status;

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

package com.dfd.vo;

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
 * @date 2023年06月27日 0027 14:42:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
@Accessors(chain = true)
public class AttendanceMonDataVO implements Serializable {

    private static final long serialVersionUID = 5099547899999541753L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", name = "id")
    private Integer id;
    /**
     * uid
     */
    @ApiModelProperty(value = "uid", name = "uid")
    private String uid;
    /**
     * 日
     */
    @ApiModelProperty(value = "日", name = "day")
    private Integer day;

    /**
     * 考勤状态 1驻现场；2项目出差；3本地办公；4休假；；
     */
    @ApiModelProperty(value = "考勤状态 1驻现场；2项目出差；3本地办公；4休假；", name = "status")
    private Integer status;

}

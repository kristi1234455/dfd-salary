package com.dfd.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dfd.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel
@Accessors(chain = true)
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1633646096188572006L;

    @ApiModelProperty(value = "用户对象", name = "user")
    private User user;

    @ApiModelProperty(value = "token", name = "token")
    private String token;
}
package com.dfd.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yy
 * @date 2023/6/7 16:10
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ApiModel(value = "职能对象")
public class ManagerDTO implements Serializable {

    private static final long serialVersionUID = 8925654125130028643L;

    /**
     * 职能：1、普通员工；2、项目管理所；3、经营所；4、设计所；5、工程所；6、部门分管领导；7、部门职能领导；8、部门负责人；
     */
    @ApiModelProperty(value = "职能：1、普通员工；2、项目管理所；3、经营所；4、设计所；5、工程所；6、部门分管领导；7、部门职能领导；8、部门负责人；", name = "manager")
    private String manager;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", name = "name")
    private String name;
}

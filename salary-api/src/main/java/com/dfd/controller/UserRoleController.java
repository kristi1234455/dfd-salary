package com.dfd.controller;


import com.dfd.dto.*;
import com.dfd.service.UserService;
import com.dfd.utils.DFDResult;
import com.dfd.utils.PageResult;
import com.dfd.vo.UserRoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "权限管理", tags = {"用于权限管理的相关接口"})
@RestController
@RequestMapping("user/role")
@CrossOrigin
public class UserRoleController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有权限用户", httpMethod = "POST")
    @PostMapping("/info")
    public DFDResult<PageResult<UserRoleVO>> infoRole(@RequestBody @Valid UserRoleInfoDTO userRoleInfoDTO) {
        return DFDResult.sucess(userService.infoRole(userRoleInfoDTO));
    }

    @ApiOperation(value = "更改用户权限", httpMethod = "POST")
    @PostMapping("/update")
    public DFDResult updateRole(@RequestBody @Valid UserRoleUpdateDTO userRoleUpdateDTO) {
        userService.updateRole(userRoleUpdateDTO);
        return DFDResult.sucess();
    }

    @ApiOperation(value = "删除权限用户", httpMethod = "POST")
    @PostMapping("/delete")
    public DFDResult deleteRole(@RequestBody @Valid UserRoleDeleteDTO userRoleDeleteDTO) {
        userService.deleteRole(userRoleDeleteDTO);
        return DFDResult.sucess();
    }


}

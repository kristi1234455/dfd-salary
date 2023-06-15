package com.dfd.controller;


import com.dfd.dto.UserLoginInDTO;
import com.dfd.dto.UserRegistDTO;
import com.dfd.dto.UserResetDTO;
import com.dfd.entity.User;
import com.dfd.service.UserService;
import com.dfd.utils.DFDResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
@CrossOrigin
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    @ApiImplicitParam(name = "phone", value = "手机号")
    public DFDResult<Boolean> usernameIsExist(@RequestParam  @NotBlank(message = "手机号不能为空")String phone) {
        return DFDResult.sucess(userService.queryPhoneIsExist(phone));
    }

    @ApiOperation(value = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public DFDResult<Integer> regist(@RequestBody @Valid UserRegistDTO userRegistDTO,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        return DFDResult.sucess(userService.createUser(userRegistDTO,request,response));
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/loginIn")
    public DFDResult<User> login(@RequestBody UserLoginInDTO userLoginDTO,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        return DFDResult.sucess(userService.loginInUser(userLoginDTO,request,response));
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/loginOut")
    @ApiImplicitParam(name = "userId", value = "userId")
    public DFDResult<User> logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        return DFDResult.sucess(userService.logOutUser(userId,request,response));
    }

    @ApiOperation(value = "密码重置", notes = "密码重置", httpMethod = "POST")
    @PostMapping("/reset")
    public DFDResult<Integer> reset(@RequestBody @Valid UserResetDTO userResetDTO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        return DFDResult.sucess(userService.resetUser(userResetDTO,request,response));
    }

}

package com.dfd.service;

//import com.imooc.pojo.Users;
//import com.imooc.pojo.bo.UserBO;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfd.dto.UserLoginInDTO;
import com.dfd.dto.UserRegistDTO;
import com.dfd.dto.UserResetDTO;
import com.dfd.entity.Attendance;
import com.dfd.entity.User;
import com.dfd.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService extends IService<User> {

    /**
     * 判断用户名是否存在
     */
    Boolean queryNumberIsExist(String phone);

    /**
     * 用户注册
     */
    Integer createUser(UserRegistDTO userRegistBO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户登录
     */
    UserVO loginInUser(UserLoginInDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户登出
     * @param userId
     * @return
     */
    public User logOutUser(String userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 密码重置
     * @param userResetDTO
     * @param request
     * @param response
     * @return
     */
    Integer resetUser(UserResetDTO userResetDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * 通过唯一手机号查询用户信息
     */
    public User selectByNumber(String number);
}

package com.dfd.service.util;

import com.alibaba.fastjson.JSON;
import com.dfd.constant.LoginConstant;
import com.dfd.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yy
 * @date 2023/6/26 9:03
 */
public class UserRequest {

    public static User getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userString = (String)request.getAttribute(LoginConstant.CURRENT_USER);
        User currentUserInfo = JSON.parseObject(userString,User.class);
        return currentUserInfo;
    }

}

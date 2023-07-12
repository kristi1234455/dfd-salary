package com.dfd.service.util;

import com.alibaba.fastjson.JSON;
import com.dfd.constant.LoginConstant;
import com.dfd.entity.User;
import com.dfd.utils.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author yy
 * @date 2023/6/26 9:03
 */
@Slf4j
public class UserRequest {

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userString = (String) request.getAttribute(LoginConstant.CURRENT_USER);
        log.info("获取到的用户信息：{}", userString);
        User currentUserInfo = StringUtils.isNotEmpty(userString) ? JSON.parseObject(userString, User.class) : null;
        if (!Optional.ofNullable(currentUserInfo).isPresent()) {
            throw new BusinessException("用户信息为空");
        }
        return currentUserInfo;
    }

}

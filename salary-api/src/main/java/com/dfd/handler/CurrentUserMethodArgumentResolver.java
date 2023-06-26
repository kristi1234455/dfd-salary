package com.dfd.handler;

import com.alibaba.fastjson.JSON;
import com.dfd.anno.CurrentUser;
import com.dfd.constant.LoginConstant;
import com.dfd.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;


/**
 * @author yy
 * @date 2023/6/25 17:13
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        // 获取拦截器中获取的当前用户信息
        String userString = (String)nativeWebRequest.getAttribute(LoginConstant.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        User currentUserInfo = JSON.parseObject(userString,User.class);
        if (currentUserInfo != null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.setAttribute(LoginConstant.CURRENT_USER,userString);
            return currentUserInfo;
        }
        // 如果当前用户信息为null  则抛出异常
        throw new MissingServletRequestPartException(LoginConstant.CURRENT_USER);
    }
}

package com.dfd.interceptor;

import com.dfd.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yy
 * @date 2023/5/11 11:13
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession httpSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("==============================LoginInterceptor begin:preHandle==============================");
        String uri = request.getRequestURI();
        log.info("当前路径："+uri);
        /**
         * HandlerMethod=>Controller中标注@PostMapping的方法
         *  需要配置静态资源不拦截时，添加这块逻辑  => 前后端分离项目
         *
         */
        // 是我们的conrtoller中的方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        log.info("token：{}",token);
        // TODO: 2023/6/5  :开启；根据token获取用户名，存放在redis中，保存时间2个小时，否则重新登录
//        if (!TokenUtil.verify(token)) {
//            // 未登录跳转到登录界面
//            throw  new RuntimeException("no login!");
//        } else {
//            return true;
//        }
        return true;
    }

    //Controller逻辑执行完毕但是视图解析器还未进行解析之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("==============================LoginInterceptor begin:postHandle==============================");
    }

    //Controller逻辑和视图解析器执行完毕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("==============================LoginInterceptor begin:afterCompletion==============================");
    }
}

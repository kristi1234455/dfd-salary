package com.dfd.interceptor;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.dfd.constant.LoginConstant;
import com.dfd.entity.User;
import com.dfd.service.UserService;
import com.dfd.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
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

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        log.info("==============================LoginInterceptor begin:preHandle==============================");
        String uri = arg0.getRequestURI();
        log.info("当前路径：{}",uri);
        /**
         * HandlerMethod=>Controller中标注@PostMapping的方法
         *  需要配置静态资源不拦截时，添加这块逻辑  => 前后端分离项目
         *
         */
        // 是我们的conrtoller中的方法
        if (!(arg2 instanceof HandlerMethod)) {
            return true;
        }

        //登录接口放行
        if (uri.contains("/passport") || uri.contains("/register") || uri.contains("/error") || uri.contains("/static")) {
            return true;
        }
        //权限路径拦截
        arg1.setContentType("text/html;charset=utf-8");
        ServletOutputStream resultWriter = arg1.getOutputStream();
        final String headerToken=arg0.getHeader("token");
        //判断请求信息
        if(null==headerToken||headerToken.trim().equals("")){
            resultWriter.write("请求头header中没有token，请登录！".getBytes());
            resultWriter.flush();
            resultWriter.close();
            return false;
        }
        // TODO: 2023/6/5  :开启；根据token获取用户名，存放在redis中，保存时间2个小时，否则重新登录
        log.info("token：{}",headerToken);
        if (!TokenUtil.verify(headerToken)) {
            // 未登录跳转到登录界面
            throw new RuntimeException("headerToken校验失败，请重新登录!");
        } else {
            Claim phone = JWT.decode(headerToken).getClaim("username");
            User currentUser = userService.selectByPhone(phone.asString());
            log.info("登录后的用户信息：{}", currentUser.toString());
            arg0.setAttribute(LoginConstant.CURRENT_USER, JSON.toJSONString(currentUser));
            return true;
        }
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

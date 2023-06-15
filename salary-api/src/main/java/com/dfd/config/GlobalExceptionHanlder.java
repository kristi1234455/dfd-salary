package com.dfd.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dfd.utils.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理.
 * @author zgk
 */
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHanlder extends GlobalBizExceptionHandler {

//    private final AdvertisingDingDingService advertisingDingDingService;
    private final ApplicationContext context;
//    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    @Autowired
    private HttpServletRequest request;

    public String getActiveProfile() {
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        if (ArrayUtil.isEmpty(activeProfiles)) {
            return "";
        }
        return activeProfiles[0];
    }

    @Override
    @ExceptionHandler({Exception.class})
    public R handleGlobalException(Exception exception) {
//        log.error("主机ip:{},发生异常:{}", nacosDiscoveryProperties.getIp(), exception.getMessage(), exception);
        try {
            String msg = ExceptionUtil.stacktraceToString(exception,500).replace("$", "");
//            advertisingDingDingService.sendBizExceptionMsg(getActiveProfile(), nacosDiscoveryProperties.getIp()+" [服务]："+nacosDiscoveryProperties.getService(),  request.getRequestURI(), msg);
        }catch (Exception e){
            log.error("发送消息异常 {}",e);
        }
        return R.failed("服务器异常");
    }


}

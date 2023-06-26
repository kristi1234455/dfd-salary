//package com.dfd.aop;
//
//import cn.hutool.core.date.DatePattern;
//import cn.hutool.core.date.DateUtil;
//import com.dfd.anno.BusLog;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
///**
// * @author yy
// * @date 2023/6/21 16:11
// */
//@Component
//@Aspect
//@Slf4j
//todo:BusLogAop日志记录
//public class BusLogAop implements Ordered {
//    @Autowired
//    private BusLogDao busLogDao;
//
//    /**
//     * 定义BusLogAop的切入点为标记@BusLog注解的方法
//     */
//    @Pointcut(value = "@annotation(com.dfd.anno.BusLog)")
//    public void pointcut() {
//    }
//
//    /**
//     * 业务操作环绕通知
//     *
//     * @param proceedingJoinPoint
//     * @retur
//     */
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
//        log.info("----BusAop 环绕通知 start");
//        //执行目标方法
//        Object result = null;
//        try {
//            result = proceedingJoinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        //目标方法执行完成后，获取目标类、目标方法上的业务日志注解上的功能名称和功能描述
//        Object target = proceedingJoinPoint.getTarget();
//        Object[] args = proceedingJoinPoint.getArgs();
//        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
//        BusLog anno1 = target.getClass().getAnnotation(BusLog.class);
//        BusLog anno2 = signature.getMethod().getAnnotation(BusLog.class);
//        BusLogBean busLogBean = new BusLogBean();
//        String logName = anno1.name();
//        String logDescrip = anno2.descrip();
//        busLogBean.setBusName(logName);
//        busLogBean.setBusDescrip(logDescrip);
//        busLogBean.setOperPerson("fanfu");
//        busLogBean.setOperTime(new Date());
//        JsonMapper jsonMapper = new JsonMapper();
//        String json = null;
//        try {
//            json = jsonMapper.writeValueAsString(args);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        //把参数报文写入到文件中
//        OutputStream outputStream = null;
//        try {try
//            String paramFilePath = System.getProperty("user.dir") + File.separator + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN) + ".log";
//            outputStream = new FileOutputStream(paramFilePath);
//            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
//            busLogBean.setParamFile(paramFilePath);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.flush();
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }printStackTrace
//        }
//        //保存业务操作日志信息
//        this.busLogDao.insert(busLogBean);
//        log.info("----BusAop 环绕通知 end");
//        return result;
//    }
//
//    @Override
//    public int getOrder() {
//        return 1;
//    }
//}

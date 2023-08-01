package com.dfd.controller;

//import com.dfd.entity.User;
//import com.dfd.utils.BusinessException;
import com.dfd.anno.BusLog;
import com.dfd.utils.TokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2023/3/29 17:19
 */
@Api(value = "内部测试", tags = {"用于内部测试的相关接口"})
@RestController
@Slf4j
@CrossOrigin
@BusLog(name = "内部日志管理测试")
@RequestMapping("hello")
public class HelloController {

    @RequestMapping("index")
    @BusLog(descrip = "内部日志管理：获取项目人员考勤状态")
    public String index(){
        String result = null;
        if(result==null){
//            throw new BusinessException("测试全局异常");
            log.info("测试全局异常");
        }
        log.info("测试成功！");
        return "测试成功";
    }

    @RequestMapping("token")
    @BusLog(descrip = "生成token")
    public String token(){
        String username ="13419876445";
        String password = "123";
        String token = TokenUtil.token(username,password);
        System.out.println(token);
        return token;
    }

    @RequestMapping("token/generate")
    @BusLog(descrip = "通过工号和密码生成token")
    public String tokenGenerate(String number, String password){
        String token = TokenUtil.token(number,password);
        System.out.println(token);
        return token;
    }
}

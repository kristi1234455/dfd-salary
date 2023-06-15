package com.dfd.controller;

//import com.dfd.entity.User;
//import com.dfd.utils.BusinessException;
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
public class HelloController {

    @RequestMapping("index")
    public String index(){
        String result = null;
        if(result==null){
//            throw new BusinessException("测试全局异常");
            log.info("测试全局异常");
        }
        log.info("测试成功！");
        return "测试成功";
    }
}

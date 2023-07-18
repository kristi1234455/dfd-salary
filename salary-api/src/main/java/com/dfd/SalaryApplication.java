package com.dfd;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yy
 * @date 2023/3/15 17:21
 */
@SpringBootApplication
@MapperScan(basePackages = "com.dfd.mapper")
@EnableSwagger2
//@ComponentScan(basePackages ={"com.dfd.config"})
public class SalaryApplication {
    public static void main(String[] args) {

        SpringApplication.run(SalaryApplication.class, args);

    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor(){
//        //1.先new一个拦截器的类
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        //2.添加一个或多个拦截器
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        //3.把拦截器返回
//        return interceptor;
//    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }

//
//
//    @Bean
//    public  Pagi
}

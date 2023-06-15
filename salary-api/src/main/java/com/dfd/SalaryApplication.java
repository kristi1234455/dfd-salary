package com.dfd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author yy
 * @date 2023/3/15 17:21
 */
@SpringBootApplication
@MapperScan(basePackages = "com.dfd.mapper")
@EnableSwagger2
public class SalaryApplication {
    public static void main(String[] args) {

        SpringApplication.run(SalaryApplication.class, args);

    }
}

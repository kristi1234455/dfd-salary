package com.dfd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yy
 * @date 2023/3/15 17:21
 */
@SpringBootApplication
@MapperScan(basePackages = "com.dfd.mapper")
@EnableSwagger2
//@EnableJpaAuditing
public class SalaryApplication {
    public static void main(String[] args) {

        SpringApplication.run(SalaryApplication.class, args);

    }
}

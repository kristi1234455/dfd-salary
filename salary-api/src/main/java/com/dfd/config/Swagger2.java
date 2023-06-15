package com.dfd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author yy
 * @date 2023/3/29 17:26
 */
@Configuration
@EnableSwagger2
public class Swagger2 implements WebMvcConfigurer {

//    http://localhost:8088/swagger-ui.html     原路径，是swagger的官方的访问路径，页面没有任何特殊效果
//    http://localhost:8088/doc.html     原路径，是swagger-bootstrap-ui包中的访问路径，页面有特殊效果，分为左右

    // 配置swagger2核心配置 docket
    //加上bean注解之后，与Configuration一起，表示这个是一个配置类对象，可以交由spring容器进行管理
    @Bean
    public Docket createRestApi() {
        //DocumentationType表示指定我们需要的是哪种文档类型，有三种，其中SWAGGER_12表示的是swagger的版本是1.2
        return new Docket(DocumentationType.SWAGGER_2)  // 指定api类型为swagger2
                .apiInfo(apiInfo())                 // 用于定义api文档汇总信息
                //swagger的选择器
                .select()
                //填入的要是所有controller的地址包
                .apis(RequestHandlerSelectors
                        .basePackage("com.dfd.controller"))   // 指定controller包
                //这里表示所有包下面的所有controller要加入swagger
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    //开发人员的相关信息，会显示在swagger的首页
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("工资申报台接口api")        // 文档页标题
                //开发人员的姓名，开发人员的博客地址或者是网站地址，联系方式
                .contact(new springfox.documentation.service.Contact("dfd",
                        "https://www.dfd.com",
                        "abc@dfd.com"))        // 联系人信息
                .description("专为工资申报提供的api文档")  // 文档的详细描述信息
                .version("1.0.0")   // 文档版本号
                .termsOfServiceUrl("https://www.dfd.com") // 网站地址
                .build();
    }

}


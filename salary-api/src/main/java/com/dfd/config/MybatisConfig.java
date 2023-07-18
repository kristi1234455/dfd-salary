package com.dfd.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * @author yy
 * @date 2023/6/25 15:22
 */
@Configuration
@EnableTransactionManagement
//@MapperScan(basePackages = "com.dfd.mapper")
public class MybatisConfig {

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor(){
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        PaginationInnerInterceptor paginationInterceptor=new PaginationInnerInterceptor();
//        interceptor.addInnerInterceptor(paginationInterceptor);
//        return interceptor;
//    }


//    @Bean
//    public PaginationInnerInterceptor paginationInnerInterceptor(){
//        return new PaginationInnerInterceptor();
//    }

//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

//    @Bean
//    public PaginationInnerInterceptor paginationInnerInterceptor() {
//        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
//        // 设置最大单页限制数量，默认 500 条，-1 不受限制
//        paginationInterceptor.setMaxLimit(-1L);
//        paginationInterceptor.setDbType(DbType.MYSQL);
//        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setOptimizeJoin(true);
//        return paginationInterceptor;
//    }
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor(){
//        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
//        mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));
//        return mybatisPlusInterceptor;
//    }


//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
//        return interceptor;
//    }

//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }

    /**
     * 自定义 SqlInjector
     * 里面包含自定义的全局方法
     */
//    @Bean
//    public MyLogicSqlInjector myLogicSqlInjector() {
//        return new MyLogicSqlInjector();
//    }

//    @Primary
//    @Bean("mysqlSqlSessionFactory")
//    public MybatisSqlSessionFactoryBean mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        configuration.addInterceptor(interceptor);
//        bean.setConfiguration(configuration);
//
//        bean.setDataSource(dataSource);
//        bean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
//        return bean;
//    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

//    @Primary
    @Bean("mysqlSqlSessionFactory")
    public MybatisSqlSessionFactoryBean mysqlSqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        configuration.addInterceptor(interceptor);
//        bean.setConfiguration(configuration);

        bean.setDataSource(dataSource);
        bean.setPlugins(mybatisPlusInterceptor());
//        bean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        return bean;
    }


}

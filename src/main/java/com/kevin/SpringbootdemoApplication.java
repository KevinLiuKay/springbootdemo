package com.kevin;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.kevin.filter.HttpContextFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzk
 */
@EnableScheduling       // 启用定时任务
@EnableTransactionManagement    // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableSwagger2
@ServletComponentScan
@EnableCaching  // 开启缓存，需要显示的指定
@Configuration
@MapperScan({"com.kevin.dao.mapper","com.kevin.dao.extMapper"})
@SpringBootApplication
public class SpringbootdemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootdemoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootdemoApplication.class);
    }

    /**
     * 配置全局的CROS解决跨域问题
     */
    @Configuration
    public class CorsConfig {
        private CorsConfiguration buildConfig() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            /**
            “*”代表全部。”**”代表适配所有接口。
             其中addAllowedOrigin(String origin)方法是追加访问源地址。
             如果不使用”*”（即允许全部访问源），则可以配置多条访问源来做控制。
             */
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge((long) 3600);
            // 1 设置访问源地址
            corsConfiguration.addAllowedOrigin("*");
            // 2 设置访问源请求头
            corsConfiguration.addAllowedHeader("*");
            // 3 设置访问源请求方法
            corsConfiguration.addAllowedMethod("*");
            return corsConfiguration;
        }

        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            // 4 对接口配置跨域设置
            source.registerCorsConfiguration("/**", buildConfig());
            return new CorsFilter(source);
        }
    }

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean httpContextFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(httpContextFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("httpContextFilter");
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "httpContextFilter")
    public javax.servlet.Filter httpContextFilter() {
        return new HttpContextFilter();
    }

    /**
     * 配置上传文件大小的配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(-1);
        /// 总上传数据大小
        factory.setMaxRequestSize(-1);
        return factory.createMultipartConfig();
    }
}

package com.kevin;

import com.kevin.interceptor.UserInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lzk
 */
@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurerAdapter.class);

    //把我们的拦截器注入为bean
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                //添加拦截规则
                .addPathPatterns("/**") //对所有请求都拦截
                //排除拦截
                .excludePathPatterns(
                        "/login/**",
                        "/swagger-ui.html/**", //swagger
                        "/swagger-resources/**", //swagger
                        "/webjars/**", //swagger
                        "/v2/**", //swagger
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/**/*.jpeg");
    }

    /**
     * 配置静态访问资源
     * 默认的静态资源的映射路径优先级顺序为：META-INF/resources > resources > static > public
     * 修改spring.mvc.static-path-pattern来修改默认的映射**
     * 自定义静态资源映射目录的话, 重写addResourceHandlers解决resources下面静态资源无法访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResoureHandler指的是对外暴露的访问路径, addResourceLocations指的是文件放置的目录
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/processes/**").addResourceLocations("classpath:/processes/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.debug("-----> addCorsMappings 设置跨域访问");
        registry.addMapping("/**")
                .allowedOrigins("*")//设置跨域访问的域名，如果是，默认都可以访问
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowCredentials(true);//服务端设置可以接收cookie信息
    }
}

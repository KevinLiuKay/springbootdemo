package com.kevin;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.kevin.interceptor.UserInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzk
 */
@Configuration
public class MyWebMvcConfigurerAdapter  extends WebMvcConfigurationSupport {
    private Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurerAdapter.class);
    /**
     * 把我们的拦截器注入为bean
     * @return
     */
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                /**
                 * 添加拦截规则
                 * 对所有请求都拦截
                 */
                .addPathPatterns("/**")
                //排除拦截
                .excludePathPatterns(
                        "/login/**",
                        "/swagger-ui.html/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
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
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
        registry.addResourceHandler("/swagger/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger*");
        registry.addResourceHandler("/v2/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/v2/api-docs/");
        super.addResourceHandlers(registry);
    }

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.debug("-----> addCorsMappings 设置跨域访问");
        registry.addMapping("/**")
                /**
                 * 设置跨域访问的域名，如果是，默认都可以访问
                 */
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                /**
                 * 服务端设置可以接收cookie信息
                 */
                .allowCredentials(true);
    }

    /**
     * 配置消息转换器--这里我用的是alibaba 开源的 fastjson
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        /**
         *     空值特别处理
         *     WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
         *     WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
         *     WriteNullNumberAsZero 将数值类型字段的空值输出为0
         *     WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
         */
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        //3.日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //4.处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //5.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //6.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
    }
}

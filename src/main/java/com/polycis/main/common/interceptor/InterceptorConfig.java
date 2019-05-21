package com.polycis.main.common.interceptor;

import com.polycis.main.client.redis.RedisFeignClient;
import com.polycis.main.common.interceptor.role.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    RedisFeignClient redisFeignClient;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/ossadmin/login")
                .excludePathPatterns("/ossadmin/logout");

        registry.addInterceptor(new TokenIntecptor(redisFeignClient)).addPathPatterns("/**")
                // 排除用户登录
                .excludePathPatterns("/ossadmin/login")
                .excludePathPatterns("/ossadmin/logout")
                // 排除swagger
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");

        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/**")
                // 排除用户登录
                .excludePathPatterns("/ossadmin/login")
                .excludePathPatterns("/ossadmin/logout")
                // 排除swagger
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");



    }


    /**
     * 排除swagger
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}

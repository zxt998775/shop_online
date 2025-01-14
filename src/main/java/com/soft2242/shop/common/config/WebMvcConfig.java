package com.soft2242.shop.common.config;

import com.soft2242.shop.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }


    //    将需要登录拦截器配置到容器中，并配置不被拦截的路径
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizationInterceptor()).addPathPatterns("/user/profile/**").addPathPatterns("/member/**").addPathPatterns("/cart/**").addPathPatterns("/member/**").addPathPatterns("/order/**");
//        registry.addInterceptor(getAuthorizationInterceptor()).addPathPatterns("/member/**");
    }
}
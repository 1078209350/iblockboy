package com.example.helloworld.config;

import com.example.helloworld.interceptor.LoginIntercetor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor( new LoginIntercetor()).addPathPatterns("/user/**");
        registry.addInterceptor(new LoginIntercetor());
    }
}

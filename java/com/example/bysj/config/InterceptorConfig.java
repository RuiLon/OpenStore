package com.example.bysj.config;

import com.example.bysj.Interceptor.JWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/consumers/login","/consumers/register","/files/**","/consumers/forgetPassword","/consumers/readOneByPhone/{phone}");
    }

    @Bean
    public JWTInterceptor jwtInterceptor()
    {
        return new JWTInterceptor();
    }
}

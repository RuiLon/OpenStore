package com.example.bysj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

/**
 * 跨域配置
 */
@Configuration
public class WebMvcConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        //服务器验证该请求的origin是否在Access-Control-Allow-Origin范围内
        //可以通过的ip，*代表所有，可以使用指定的ip，多个的话可以用逗号分隔，默认为*
        config.addAllowedOriginPattern("*");

        //支持证书，默认为true
        config.setAllowCredentials(true);

        //请求方法是否在Access-Control-Allow-Methods范围内
        //请求方式 默认为*,所有
        config.addAllowedMethod("*");

        //请求头是否都在Access-Control-Allow-Headers范围内
        //请求支持的头信息，默认为*，所有
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();

        // CORS 配置对所有接口都有效
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

}

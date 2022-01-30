package com.donaldo.site.backend.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Description("Add new security interceptor")
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor());
    }
}

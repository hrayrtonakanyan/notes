package com.disqo.assessment.notes.adapters;

import com.disqo.assessment.notes.interceptors.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 1:43 AM.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/v1/auth/**");
    }

    @Bean
    public AuthInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }
}

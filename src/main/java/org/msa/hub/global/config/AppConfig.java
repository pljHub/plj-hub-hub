package org.msa.hub.global.config;

import lombok.RequiredArgsConstructor;
import org.msa.hub.global.login.LoginArgumentResolver;
import org.msa.hub.global.pageable.CustomPageableArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private final LoginArgumentResolver loginArgumentResolver;
    private final CustomPageableArgumentResolver pageableArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
        resolvers.add(pageableArgumentResolver);
    }
}

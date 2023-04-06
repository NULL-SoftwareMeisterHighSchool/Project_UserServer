package com.project.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public MappingJackson2JsonView jsonView() {
        return new MappingJackson2JsonView();
    }

}
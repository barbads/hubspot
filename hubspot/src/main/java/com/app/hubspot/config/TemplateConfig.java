package com.app.hubspot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TemplateConfig {

    /**
     * criando um BEAN para que uma ÚNICA instância seja gerada
     * pelo Spring, economizando recursos.
     * @return RestTamplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
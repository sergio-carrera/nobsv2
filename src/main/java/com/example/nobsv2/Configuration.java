package com.example.nobsv2;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    /*
    Los Beans son inyectados en el container de Spring
    Esto nos da acceso al RestTemplate a través de la aplicación
    */
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

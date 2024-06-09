package com.example.orchestrateur2.config; 

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Bean
    @Qualifier("service4")
    public WebClient service2Client(@Value("${service4.endpoint}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

    @Bean
    @Qualifier("service5")
    public WebClient service3Client(@Value("${service5.endpoint}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }



}

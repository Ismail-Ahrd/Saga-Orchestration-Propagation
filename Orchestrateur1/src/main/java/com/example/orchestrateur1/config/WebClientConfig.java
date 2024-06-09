package com.example.orchestrateur1.config; 

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Bean
    @Qualifier("service2")
    public WebClient service2Client(@Value("${service2.endpoint}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

    @Bean
    @Qualifier("service3")
    public WebClient service3Client(@Value("${service3.endpoint}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

    @Bean
    @Qualifier("orchestrator2")
    public WebClient orchestrator2Client(@Value("${orchestrator2.endpoint}") String endpoint){
        return WebClient.builder()
                .baseUrl(endpoint)
                .build();
    }

}

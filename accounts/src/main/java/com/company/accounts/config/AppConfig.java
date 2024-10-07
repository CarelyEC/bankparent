package com.company.accounts.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${config.webclient.host}")
    private String url;

    @Bean
    public WebClient webClient() {
        logger.info("URL configurada para WebClient: {}", url);  // Imprime la URL
        return WebClient.create(url);
    }

}

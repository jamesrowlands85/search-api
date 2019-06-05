package com.rowlands.searchapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "search")
@Getter
@Setter
public class SearchProperties {

    private String apiKey;
    private String searchEngineId;
    private int connectTimeout;
    private int readTimeout;
}

package com.atguigu.java.ai.langchain4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "elastic.serverless")
@Data
public class ElasticsearchProperty {

    private String endpoint;

    private String apiKey;
}

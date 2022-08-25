package com.lujun61.fmmall.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        HttpHost httpHost = new HttpHost("192.168.153.130", 9200, "http");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        return new RestHighLevelClient(restClientBuilder);
    }

}


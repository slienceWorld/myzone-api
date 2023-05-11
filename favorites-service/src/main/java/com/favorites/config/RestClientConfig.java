package com.favorites.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * ElasticSearch 客户端配置
 *
 * @author geng
 * 2020/12/19
 */

@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {



    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("124.221.194.98", 9200, "http"),
                new HttpHost("124.221.194.98", 9300, "http")
        ));

    }
}

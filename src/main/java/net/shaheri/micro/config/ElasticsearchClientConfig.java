package net.shaheri.micro.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages
        = "net.shaheri.micro.repository")
@ComponentScan
public class ElasticsearchClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${app.elastic.addr}")
    private String addr;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration =
                ClientConfiguration
                        .builder()
                        .connectedTo(addr)
                        .build();
        return RestClients.create(clientConfiguration).rest();
    }
}
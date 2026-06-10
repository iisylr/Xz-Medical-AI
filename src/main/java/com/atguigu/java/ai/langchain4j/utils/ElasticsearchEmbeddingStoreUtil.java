package com.atguigu.java.ai.langchain4j.utils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.atguigu.java.ai.langchain4j.properties.ElasticsearchProperty;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchEmbeddingStoreUtil {

    @Autowired
    private ElasticsearchProperty elasticsearchProperty;

    /**
     * 创建 Elasticsearch 向量存储
     * @param elasticsearchClient Elasticsearch Client
     * @param indexName 索引名称
     * @return Elasticsearch 向量存储
     */
    public ElasticsearchEmbeddingStore createElasticsearchEmbeddingStore(
            ElasticsearchClient elasticsearchClient, String indexName
    ) {
        return ElasticsearchEmbeddingStore.builder()
                .client(elasticsearchClient)
                .indexName(indexName)
                .build();
    }

    /**
     * 创建 Rest Client
     * @return Rest Client
     */
    @Deprecated(forRemoval = true)
    public RestClient createRestClient() {

        return RestClient.builder(HttpHost.create(elasticsearchProperty.getEndpoint()))
                .setDefaultHeaders(
                        new Header[] {
                                new BasicHeader(
                                        "Authorization", "ApiKey " + elasticsearchProperty.getApiKey()
                                )
                        }
                ).build();
    }

    /**
     * 创建 Elasticsearch 客户端
     * @return Elasticsearch Client
     */
    public ElasticsearchClient createElasticsearchClient() {
        RestClient restClient = RestClient.builder(HttpHost.create(elasticsearchProperty.getEndpoint()))
                .setDefaultHeaders(
                        new Header[] {
                                new BasicHeader(
                                        "Authorization", "ApiKey " + elasticsearchProperty.getApiKey()
                                )
                        }
                ).build();

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}

package com.atguigu.java.ai.langchain4j.config;

import com.atguigu.java.ai.langchain4j.store.MongoChatMemoryStore;
import com.atguigu.java.ai.langchain4j.utils.ElasticsearchEmbeddingStoreUtil;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.elasticsearch.ElasticsearchContentRetriever;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchConfigurationKnn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XZAgentConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private ElasticsearchEmbeddingStoreUtil elasticsearchEmbeddingStoreUtil;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    public ChatMemoryProvider chatMemoryProviderXZ() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

    /*
    @Bean
    public ContentRetriever contentRetrieverXZ() {
        Document document1 = FileSystemDocumentLoader.loadDocument(
                "D:/Code/IntelliJIDEA/Guigu Xiaozhi Medical/resource/knowledge/医院信息.md");

        Document document2 = FileSystemDocumentLoader.loadDocument(
                "D:/Code/IntelliJIDEA/Guigu Xiaozhi Medical/resource/knowledge/科室信息.md");

        Document document3 = FileSystemDocumentLoader.loadDocument(
                "D:/Code/IntelliJIDEA/Guigu Xiaozhi Medical/resource/knowledge/神经内科.md");

        List<Document> documentList = List.of(document1, document2, document3);

        // 暂时使用基于内存的向量储存
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        EmbeddingStoreIngestor.ingest(documentList, embeddingStore);

        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }
    */

    /**
     * 创建 Elasticsearch 内容检索器
     * @return Elasticsearch 内容检索器
     */
    @Bean
    public ElasticsearchContentRetriever elasticsearchContentRetriever() {
        return ElasticsearchContentRetriever.builder()
                .client(elasticsearchEmbeddingStoreUtil.createElasticsearchClient())
                .embeddingModel(embeddingModel)
                .configuration(ElasticsearchConfigurationKnn.builder().build())
                .indexName("xiaozhi-agent")
                .maxResults(1)
                .minScore(0.8)
                .filter(null)
                .build();
    }
}

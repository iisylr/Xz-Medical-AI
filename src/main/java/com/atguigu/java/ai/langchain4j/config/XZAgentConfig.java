package com.atguigu.java.ai.langchain4j.config;

import com.atguigu.java.ai.langchain4j.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class XZAgentConfig {

    @Autowired private MongoChatMemoryStore mongoChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMemoryProviderXZ() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

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
}

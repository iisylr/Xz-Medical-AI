package com.atguigu.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        // chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXZ",
        tools = "appointmentTools",
        contentRetriever = "elasticsearchContentRetriever"
)
public interface XZAgent {

    @SystemMessage(fromResource = "XZAgentPrompt.txt")
    Flux<String> chat(@MemoryId Integer memoryId, @UserMessage String message);
}

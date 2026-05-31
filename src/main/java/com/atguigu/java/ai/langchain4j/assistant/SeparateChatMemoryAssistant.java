package com.atguigu.java.ai.langchain4j.assistant;

import dev.langchain4j.service.MemoryId;
// import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
// import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "openAiChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface SeparateChatMemoryAssistant {

    String chat(@MemoryId Integer memoryId, @UserMessage String message);

//    @SystemMessage(fromResource = "prompt-template.txt")
//    String chatWithPrompt(
//            @MemoryId Integer memoryId,
//            @UserMessage String message,
//            @V("username") String username,
//            @V("age") int age
//    );
}

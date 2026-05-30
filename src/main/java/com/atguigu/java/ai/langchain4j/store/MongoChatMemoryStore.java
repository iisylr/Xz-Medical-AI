package com.atguigu.java.ai.langchain4j.store;

import com.atguigu.java.ai.langchain4j.bean.PersistentChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired private MongoTemplate mongoTemplate;

    /**
     * Retrieves messages for a specified chat memory.
     *
     * @param memoryId The ID of the chat memory.
     * @return List of messages for the specified chat memory. Must not be null.
     *         Can be deserialized from JSON using {@link ChatMessageDeserializer}.
     */
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);

        PersistentChatMessages persistentChatMessages = mongoTemplate.findOne(query, PersistentChatMessages.class);
        if (persistentChatMessages == null) {
            return new ArrayList<>();
        }

        return ChatMessageDeserializer.messagesFromJson(persistentChatMessages.getContent());
    }

    /**
     * Updates or inserts messages for a specified chat memory.
     *
     * @param memoryId The ID of the chat memory.
     * @param messages List of messages for the specified chat memory,
     *                 that represent the current state of the {@link ChatMemory}.
     *                 Can be serialized to JSON using {@link ChatMessageSerializer}.
     */
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);

        String json = ChatMessageSerializer.messagesToJson(messages);

        Update update = new Update().set("content", json);
        mongoTemplate.upsert(query, update, PersistentChatMessages.class);
    }

    /**
     * Deletes all messages for a specified chat memory.
     *
     * @param memoryId The ID of the chat memory.
     */
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, PersistentChatMessages.class);
    }
}

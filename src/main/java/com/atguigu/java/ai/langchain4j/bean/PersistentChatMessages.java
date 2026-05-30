package com.atguigu.java.ai.langchain4j.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatMessages")
public class PersistentChatMessages {

    // 映射到 MongoDB 中的 _id 字段，使用 ObjectId 类型自动生成
    @Id private ObjectId id;

    private String memoryId;

    private String content;
}

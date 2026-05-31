package com.atguigu.java.ai.langchain4j.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ChatFormDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer memoryId;

    private String message;
}

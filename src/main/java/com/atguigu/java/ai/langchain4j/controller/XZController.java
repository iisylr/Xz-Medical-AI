package com.atguigu.java.ai.langchain4j.controller;

import com.atguigu.java.ai.langchain4j.assistant.XZAgent;
import com.atguigu.java.ai.langchain4j.bean.ChatFormDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xz")
@Tag(name = "Guigu XZ")
@Slf4j
public class XZController {

    @Autowired private XZAgent xzAgent;

    @PostMapping("/chat")
    @Operation(summary = "对话")
    public String chat(@RequestBody ChatFormDTO chatFormDTO) {
        log.info("对话信息：{}", chatFormDTO);
        return xzAgent.chat(chatFormDTO.getMemoryId(), chatFormDTO.getMessage());
    }
}

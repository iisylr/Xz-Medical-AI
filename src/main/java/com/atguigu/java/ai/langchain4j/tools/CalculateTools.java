package com.atguigu.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.springframework.stereotype.Component;

@Component
public class CalculateTools {

    @Tool(name = "加法运算", value = "对输入的两个数字 x 和 y 进行加法运算")
    public static double sum(
            @ToolMemoryId Integer memoryId,
            @P(value = "x") double x,
            @P(value = "y") double y) {
        System.out.println("调用 CalculateTools.sum，memoryId = " + memoryId);
        return x + y;
    }

    @Tool(name = "平方根运算", value = "对输入的数字 x 进行平方根运算")
    public static double squareRoot(
            @ToolMemoryId Integer memoryId,
            @P(value = "x") double x) {
        System.out.println("调用 CalculateTools.squareRoot，memoryId = " + memoryId);
        return Math.sqrt(x);
    }
}

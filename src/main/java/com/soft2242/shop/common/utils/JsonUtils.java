package com.soft2242.shop.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将对象转换为 JSON 字符串
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    // 将 JSON 字符串转换为指定类型的对象
    public static <T> T toObject(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }

    // 将 JSON 字符串转换为 Map<String, Object>
    public static Map<String, Object> toMap(String json) throws IOException {
        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
    }

    // 其他可能的转换方法根据需求添加

}

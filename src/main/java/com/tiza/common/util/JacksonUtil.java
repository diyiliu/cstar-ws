package com.tiza.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Description: JacksonUtil
 * Author: DIYILIU
 * Update: 2016-03-22 9:25
 */
public class JacksonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {

        String rs = null;

        try {
            rs = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static <T> T toObject(String content, Class<T> clazz) throws IOException {

        return mapper.readValue(content, clazz);
    }


    /**
     * 有问题，会报错！！！！
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    public static List toList(String content, Class clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);

        return objectMapper.readValue(content, javaType);
    }
}

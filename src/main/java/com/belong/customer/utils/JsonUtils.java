package com.belong.customer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
JsonUtils class is defined to make ourself easy while dealing with Model functionalities
*/

public class JsonUtils {

    private static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @param filePath
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T jsonFileToModel(final String filePath, final Class<T> tClass) {
        String jsonContent = readJsonFile(filePath);
        return stringToModel(jsonContent, tClass);
    }

    /**
     * @param path
     * @return
     */
    public static String readJsonFile(final String path) {
        try {
            ClassPathResource classPathResource = new ClassPathResource(path);
            InputStream inputStream = classPathResource.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T stringToModel(final String json, final Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonString
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToModelList(final String jsonString, final Class<T> tClass) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        try {
            return MAPPER.readValue(jsonString, collectionType);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}

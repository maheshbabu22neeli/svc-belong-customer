package com.belong.customer.utils;

import com.belong.customer.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T jsonFileToModel(final String filePath, final Class<T> tClass) {
        String jsonContent = readJsonFile(filePath);
        return stringToModel(jsonContent, tClass);
    }

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

    public static <T> T stringToModel(final String json, final Class<T> tClass) {
        try {
            return MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode convertModelToJsonNode(Customer customer) {
        try {
            return MAPPER.readValue(customer.toString(), JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> stringToModelList(String jsonString, Class<T> tClass) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, tClass);
        try {
            return MAPPER.readValue(jsonString, collectionType);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}

package com.belong.customer.utils;

import com.belong.customer.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonUtils {

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T jsonFileToModel(final String filePath, final Class<T> tClass) {
        String jsonContent = readJsonFile(filePath);
        return stringToModel(jsonContent, tClass);
    }

    private static String readJsonFile(final String path) {
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

    private static <T> T stringToModel(final String json, final Class<T> tClass) {
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

    public static <T> T convertStringToModel(final String value,
                                             final Class<T> customerClass) {
        try {
            return MAPPER.readValue(value, customerClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }
}

package com.belong.customer.model.base;

import com.belong.customer.controller.PhoneNumberController;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


public abstract class AbstractModel implements Serializable {

    public static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public String toString() {
        return fromModelToString();
    }

    private String fromModelToString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            final String message = String.format("Error in Serializing the object of class %s JSON",
                    this.getClass().getName());
            LOGGER.warn(message, jsonProcessingException);
            jsonProcessingException.printStackTrace();
            return "{}";
        }
    }
}

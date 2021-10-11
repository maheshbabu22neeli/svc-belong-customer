package com.belong.customer.utils;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class JsonUtilsTest {

    @Test
    public void test_readJsonFile_return_valid_string() {
        String string = JsonUtils.readJsonFile("mockData/all_phone_numbers.json");
        Assert.assertNotNull(string);
    }

    @Test
    public void test_readJsonFile_return_null_fileNotFound_exception() {
        String string = JsonUtils.readJsonFile("mockData/no_file.json");
        Assert.assertNull(string);
    }

    @Test
    public void test_jsonFileToModel_return_valid_customer_model() {
        Customer customer = JsonUtils.jsonFileToModel("mockData/customer.json", Customer.class);
        Assert.assertNotNull(customer);
    }

    @Test
    public void test_stringToModel_return_model_from_string() {
        String customerString = JsonUtils.readJsonFile("mockData/customer.json");
        Customer customer = JsonUtils.stringToModel(customerString, Customer.class);
        Assert.assertNotNull(customer);
    }

    @Test
    public void test_stringToModel_return_null_throws_JsonProcessingException() {
        String customerString = JsonUtils.readJsonFile("mockData/customer_invalid.json");
        // loaded customer object, but expecting phone, hence throws JsonProcessingException
        Phone phone = JsonUtils.stringToModel(customerString, Phone.class);
        Assert.assertNull(phone);
    }

    @Test
    public void test_stringToModelList_return_list_of_models_from_string() {
        String phoneListString = JsonUtils.readJsonFile("mockData/all_phone_numbers.json");
        List<Phone> phoneList = JsonUtils.stringToModelList(phoneListString, Phone.class);
        Assert.assertNotNull(phoneList);
    }

    @Test
    public void test_stringToModelList_return_null_from_string() {
        String phoneListString = JsonUtils.readJsonFile("mockData/all_phone_numbers_invalid.json");
        // loaded phone object, but expecting customer, hence throws JsonProcessingException
        List<Customer> phoneList = JsonUtils.stringToModelList(phoneListString, Customer.class);
        Assert.assertNull(phoneList);
    }

}

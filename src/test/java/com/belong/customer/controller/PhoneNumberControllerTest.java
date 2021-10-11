package com.belong.customer.controller;

import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
import com.belong.customer.model.State;
import com.belong.customer.service.PhoneNumberService;
import com.belong.customer.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberControllerTest {

    @Mock
    private PhoneNumberService phoneNumberService;

    @Mock
    private PhoneNumberHelper phoneNumberHelper;

    @InjectMocks
    PhoneNumberController phoneNumberController;

    @Before
    public void init() {

    }

    @Test
    public void test_getPhoneNumbers_where_invalid_customerId() {

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "invalid customerId, required format is CUS[0-9]{3}");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.getPhoneNumbers("12345");
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, phoneNumberResponse.getErrorCode().intValue());
        Assert.assertEquals("invalid customerId, required format is CUS[0-9]{3}", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_getPhoneNumbers_throws_exception() {

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                500, "Something went wrong, please contact team");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.when(phoneNumberService.getPhoneNumbers(null)).thenThrow(IllegalArgumentException.class);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createFailureResponse(
                mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.getPhoneNumbers(null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
        Assert.assertEquals("Something went wrong, please contact team", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_getPhoneNumbers_where_customerId_null_return_all_PhoneNumbers() {

        String jsonString = JsonUtils.readJsonFile("/mockData/all_phone_numbers.json");
        List<Phone> mockPhoneList = JsonUtils.stringToModelList(jsonString, Phone.class);
        Assert.assertNotNull(mockPhoneList);

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(mockPhoneList);
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.OK);

        Mockito.doReturn(mockResponseEntity).when(phoneNumberService).getPhoneNumbers(null);

        ResponseEntity<?> responseEntity = phoneNumberController.getPhoneNumbers(null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(15, phoneNumberResponse.getRecords().intValue());
    }

    @Test
    public void test_getPhoneNumbers_where_customerId_notnull_return_all_PhoneNumbers() {

        String jsonString = JsonUtils.readJsonFile("/mockData/customer.json");
        Customer mockCustomer = JsonUtils.stringToModel(jsonString, Customer.class);
        Assert.assertNotNull(mockCustomer);

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(mockCustomer.getPhonesList());
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.OK);

        Mockito.doReturn(mockResponseEntity).when(phoneNumberService).getPhoneNumbers("CUS001");

        ResponseEntity<?> responseEntity = phoneNumberController.getPhoneNumbers("CUS001");
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(3, phoneNumberResponse.getRecords().intValue());
    }



    //===================================
    // Activation Number Test Cases
    //===================================

    @Test
    public void test_activatePhoneNumber_where_phoneNumber_null() {

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "phoneNumber cannot be Empty");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber(null, null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("phoneNumber cannot be Empty", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_where_phoneNumber_invalid_format() {

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "invalid phoneNumber, required format [0-9]{10}");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber("23423423423423", null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("invalid phoneNumber, required format [0-9]{10}", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_where_state_object_null() {

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "activatePhoneNumber body cannot be Empty");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber("0469001001", null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("activatePhoneNumber body cannot be Empty", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_where_state_value_null() {

        State state = new State("");
        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "phone state cannot be Empty");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber("0469001001", state);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("phone state cannot be Empty", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_where_state_invalid() {

        State state = new State("initial");
        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                400, "invalid phone state, require ENUM are Active or InActive");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper)
                .createFailureResponse(mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber("0469001001", state);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals("invalid phone state, require ENUM are Active or InActive", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_throws_exception() {

        State state = new State("Active");
        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(
                500, "Something went wrong, please contact team");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse,
                HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.when(phoneNumberService.activatePhoneNumber("0469001001", state.getState())).thenThrow(IllegalArgumentException.class);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createFailureResponse(
                mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber("0469001001", state);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
        Assert.assertEquals("Something went wrong, please contact team", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_return_updated_customer_details() {

        String phoneNumber = "0469001001";
        State state = new State("Active");

        String customerString = JsonUtils.readJsonFile("/mockData/customer.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerString, Customer.class);
        ResponseEntity<Customer> mockResponseEntity = new ResponseEntity<>(mockCustomer, HttpStatus.OK);

        Mockito.doReturn(mockResponseEntity).when(phoneNumberService).activatePhoneNumber(phoneNumber, state.getState());

        ResponseEntity<?> responseEntity = phoneNumberController.activatePhoneNumber(phoneNumber, state);
        Customer customer = (Customer) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customer);
        Assert.assertNotNull(customer.getPhonesList());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());

        customer.getPhonesList().forEach(phone -> {
            if (phone.getNumber().equals(phoneNumber)) {
                Assert.assertEquals("Active", phone.getState());
            }
        });
    }

}

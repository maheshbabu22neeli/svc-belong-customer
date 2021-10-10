package com.belong.customer.service;


import com.belong.customer.dao.CustomerDao;
import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
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
public class PhoneNumberServiceTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private PhoneNumberHelper phoneNumberHelper;

    @InjectMocks
    private PhoneNumberService phoneNumberService;


    @Before
    public void init() {

    }


    // ================================
    // GET Phone Number Test Cases
    // ================================

    @Test
    public void test_getPhoneNumbers_customer_id_null_return_all_PhoneNumbers() {

        String jsonString = JsonUtils.readJsonFile("/mockData/all_phone_numbers.json");
        List<Phone> mockPhoneList = JsonUtils.stringToModelList(jsonString, Phone.class);
        Assert.assertNotNull(mockPhoneList);

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(mockPhoneList);
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse, HttpStatus.OK);

        Mockito.doReturn(mockPhoneList).when(customerDao).getPhoneNumbers();
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createSuccessResponse(mockPhoneList, HttpStatus.OK);

        ResponseEntity<?> responseEntity = phoneNumberService.getPhoneNumbers(null);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(15, phoneNumberResponse.getRecords().intValue());
    }

    @Test
    public void test_getPhoneNumbers_customer_id_notnull_return_only_customer_phone_numbers() {

        String customerId = "CUS001";
        String jsonString = JsonUtils.readJsonFile("/mockData/customer_phone_numbers.json");
        List<Phone> mockPhoneList = JsonUtils.stringToModelList(jsonString, Phone.class);
        Assert.assertNotNull(mockPhoneList);

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(mockPhoneList);
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse, HttpStatus.OK);

        Mockito.doReturn(mockPhoneList).when(customerDao).getPhoneNumbersByCustomerId(customerId);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createSuccessResponse(mockPhoneList, HttpStatus.OK);

        ResponseEntity<?> responseEntity = phoneNumberService.getPhoneNumbers(customerId);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(3, phoneNumberResponse.getRecords().intValue());
    }

    @Test
    public void test_getPhoneNumbers_customer_id_not_found() {

        String customerId = "CUS000";

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(404, "customerId Not Found");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse, HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(null).when(customerDao).getPhoneNumbersByCustomerId(customerId);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createFailureResponse(
                mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberService.getPhoneNumbers(customerId);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        Assert.assertEquals("customerId Not Found", phoneNumberResponse.getErrorMessage());
    }

    // ================================
    // Activate PhoneNumber Test Cases
    // ================================

    @Test
    public void test_activatePhoneNumber_phoneNumber_not_found() {

        String phoneNumber = "0469001001";
        String state = "Active";

        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(404, "phone number not found, please check");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse, HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(null).when(customerDao).getCustomerByPhoneNumber(phoneNumber);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createFailureResponse(
                mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberService.activatePhoneNumber(phoneNumber, state);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        Assert.assertEquals("phone number not found, please check", phoneNumberResponse.getErrorMessage());
    }

    @Test
    public void test_activatePhoneNumber_phoneNumber_found_and_activated() {

        String phoneNumber = "0469001001";
        String state = "Active";

        String customerString = JsonUtils.readJsonFile("/mockData/customer_inactive_number.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerString, Customer.class);
        ResponseEntity<Customer> mockResponseEntity = new ResponseEntity<>(mockCustomer, HttpStatus.OK);

        Mockito.doReturn(mockCustomer).when(customerDao).getCustomerByPhoneNumber(phoneNumber);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createSuccessResponse(mockCustomer, HttpStatus.OK);

        ResponseEntity<?> responseEntity = phoneNumberService.activatePhoneNumber(phoneNumber, state);
        Customer customer = (Customer) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customer);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        customer.getPhonesList().forEach(phone -> {
            if (phone.getNumber().equals(phoneNumber)) {
                Assert.assertEquals("Active", phone.getState());
            }
        });
    }

    @Test
    public void test_activatePhoneNumber_phoneNumber_found_and_already_activated() {

        String phoneNumber = "0469001001";
        String state = "Active";

        String customerString = JsonUtils.readJsonFile("/mockData/customer_active_number.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerString, Customer.class);
        PhoneNumberResponse mockPhoneNumberResponse = new PhoneNumberResponse(400,
                "phone number already in active state");
        ResponseEntity<PhoneNumberResponse> mockResponseEntity = new ResponseEntity<>(mockPhoneNumberResponse, HttpStatus.valueOf(mockPhoneNumberResponse.getErrorCode()));

        Mockito.doReturn(mockCustomer).when(customerDao).getCustomerByPhoneNumber(phoneNumber);
        Mockito.doReturn(mockResponseEntity).when(phoneNumberHelper).createFailureResponse(
                mockPhoneNumberResponse.getErrorCode(), mockPhoneNumberResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = phoneNumberService.activatePhoneNumber(phoneNumber, state);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, phoneNumberResponse.getErrorCode().intValue());
        Assert.assertEquals("phone number already in active state", phoneNumberResponse.getErrorMessage());

    }

}

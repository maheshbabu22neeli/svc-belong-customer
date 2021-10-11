package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
import com.belong.customer.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberHelperTest {

    @InjectMocks
    private PhoneNumberHelper phoneNumberHelper;

    @Test
    public void test_createSuccessResponse_return_phonenumber_list() {

        String phoneListString = JsonUtils.readJsonFile("/mockData/all_phone_numbers.json");
        List<Phone> mockPhoneList = JsonUtils.stringToModelList(phoneListString, Phone.class);

        ResponseEntity<?> responseEntity = phoneNumberHelper.createSuccessResponse(mockPhoneList, HttpStatus.OK);
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(15, phoneNumberResponse.getRecords().intValue());
        Assert.assertEquals(15, phoneNumberResponse.getPhoneList().size());
    }

    @Test
    public void test_createSuccessResponse_return_updated_customer_after_activation() {

        String customerString = JsonUtils.readJsonFile("/mockData/customer.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerString, Customer.class);

        ResponseEntity<?> responseEntity = phoneNumberHelper.createSuccessResponse(mockCustomer, HttpStatus.OK);
        Customer customer = (Customer) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customer);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(3, customer.getPhonesList().size());
    }

    @Test
    public void test_createFailureResponse_return_error_response() {

        ResponseEntity<?> responseEntity = phoneNumberHelper.createFailureResponse( 400, "invalid phoneNumber, required format [0-9]{10}");
        PhoneNumberResponse phoneNumberResponse = (PhoneNumberResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals(400, phoneNumberResponse.getErrorCode().intValue());
        Assert.assertEquals("invalid phoneNumber, required format [0-9]{10}", phoneNumberResponse.getErrorMessage());
    }

}

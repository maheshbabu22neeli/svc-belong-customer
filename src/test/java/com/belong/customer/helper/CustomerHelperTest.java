package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
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
public class CustomerHelperTest {

    @InjectMocks
    private CustomerHelper customerHelper;

    @Test
    public void test_createSuccessResponse_return_customer_list() {

        String customerListString = JsonUtils.readJsonFile("/mockData/customers.json");
        List<Customer> mockCustomerList = JsonUtils.stringToModelList(customerListString, Customer.class);

        ResponseEntity<?> responseEntity = customerHelper.createSuccessResponse(mockCustomerList, HttpStatus.OK);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(5, customerResponse.getRecords().intValue());
        Assert.assertEquals(5, customerResponse.getCustomerList().size());
    }

    @Test
    public void test_createSuccessResponse_return_updated_customer_after_activation() {

        String customerString = JsonUtils.readJsonFile("/mockData/customer.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerString, Customer.class);

        ResponseEntity<?> responseEntity = customerHelper.createSuccessResponse(mockCustomer, HttpStatus.OK);
        Customer customer = (Customer) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customer);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(3, customer.getPhonesList().size());
    }

    @Test
    public void test_createFailureResponse_return_error_response() {

        ResponseEntity<?> responseEntity = customerHelper.createFailureResponse( 400, "invalid customerId, required format is CUS[0-9]{3}");
        CustomerResponse phoneNumberResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(phoneNumberResponse);
        Assert.assertEquals(400, responseEntity.getStatusCodeValue());
        Assert.assertEquals(400, phoneNumberResponse.getErrorCode().intValue());
        Assert.assertEquals("invalid customerId, required format is CUS[0-9]{3}", phoneNumberResponse.getErrorMessage());
    }

}

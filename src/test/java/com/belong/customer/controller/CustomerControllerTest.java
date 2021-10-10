package com.belong.customer.controller;

import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
import com.belong.customer.service.CustomerService;
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
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerHelper customerHelper;

    @InjectMocks
    private CustomerController customerController;

    @Before
    public void init() {

    }

    @Test
    public void test_getCustomers_where_customerId_null() {

        String customerListString = JsonUtils.readJsonFile("/mockData/customers.json");
        List<Customer> customerList = JsonUtils.stringToModelList(customerListString, Customer.class);
        Assert.assertNotNull(customerList);
        CustomerResponse mockCustomerResponse = new CustomerResponse(customerList);
        ResponseEntity<CustomerResponse> mockResponseEntity = new ResponseEntity<>(mockCustomerResponse, HttpStatus.OK);

        Mockito.doReturn(mockResponseEntity).when(customerService).getCustomers(null);

        ResponseEntity<?> responseEntity = customerController.getCustomers(null);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(5, customerResponse.getRecords().intValue());
    }

    @Test
    public void test_getCustomers_throws_exception() {

        CustomerResponse mockCustomerResponse = new CustomerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong, please contact team");
        ResponseEntity<CustomerResponse> mockResponseEntity = new ResponseEntity<>(mockCustomerResponse,
                HttpStatus.valueOf(mockCustomerResponse.getErrorCode()));

        Mockito.when(customerService.getCustomers(null)).thenThrow(IllegalArgumentException.class);
        Mockito.doReturn(mockResponseEntity).when(customerHelper).createFailureResponse(
                mockCustomerResponse.getErrorCode(), mockCustomerResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = customerController.getCustomers(null);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(500, responseEntity.getStatusCodeValue());
        Assert.assertEquals(500, customerResponse.getErrorCode().intValue());
        Assert.assertEquals("Something went wrong, please contact team", customerResponse.getErrorMessage());
    }
}

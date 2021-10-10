package com.belong.customer.service;

import com.belong.customer.dao.CustomerDao;
import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
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
public class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CustomerHelper customerHelper;

    @InjectMocks
    private CustomerService customerService;

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

        Mockito.doReturn(customerList).when(customerDao).getCustomers();
        Mockito.doReturn(mockResponseEntity).when(customerHelper).createSuccessResponse(customerList, HttpStatus.OK);

        ResponseEntity<?> responseEntity = customerService.getCustomers(null);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(5, customerResponse.getRecords().intValue());
    }

    @Test
    public void test_getCustomers_where_customerId_null_no_records_found() {

        String customerListString = JsonUtils.readJsonFile("/mockData/customers.json");
        List<Customer> customerList = JsonUtils.stringToModelList(customerListString, Customer.class);
        Assert.assertNotNull(customerList);
        CustomerResponse mockCustomerResponse = new CustomerResponse(HttpStatus.NOT_FOUND.value(), "customer records not found");
        ResponseEntity<CustomerResponse> mockResponseEntity = new ResponseEntity<>(mockCustomerResponse, HttpStatus.valueOf(mockCustomerResponse.getErrorCode()));

        Mockito.doReturn(null).when(customerDao).getCustomers();
        Mockito.doReturn(mockResponseEntity).when(customerHelper).createFailureResponse(mockCustomerResponse.getErrorCode(), mockCustomerResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = customerService.getCustomers(null);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        Assert.assertEquals(404, customerResponse.getErrorCode().intValue());
        Assert.assertEquals("customer records not found", customerResponse.getErrorMessage());
    }

    @Test
    public void test_getCustomers_where_customerId_notnull() {

        String customerId = "CUS001";

        String customerListString = JsonUtils.readJsonFile("/mockData/customer.json");
        Customer mockCustomer = JsonUtils.stringToModel(customerListString, Customer.class);
        Assert.assertNotNull(mockCustomer);
        ResponseEntity<Customer> mockResponseEntity = new ResponseEntity<>(mockCustomer, HttpStatus.OK);

        Mockito.doReturn(mockCustomer).when(customerDao).getCustomerById(customerId);
        Mockito.doReturn(mockResponseEntity).when(customerHelper).createSuccessResponse(mockCustomer, HttpStatus.OK);

        ResponseEntity<?> responseEntity = customerService.getCustomers(customerId);
        Customer customer = (Customer) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customer);
        Assert.assertEquals("CUS001", customer.getId());
        Assert.assertEquals("Mahesh Babu", customer.getName());
    }

    @Test
    public void test_getCustomers_where_customerId_notnull_no_record_found() {

        String customerId = "CUS000";

        String customerListString = JsonUtils.readJsonFile("/mockData/customers.json");
        List<Customer> customerList = JsonUtils.stringToModelList(customerListString, Customer.class);
        Assert.assertNotNull(customerList);
        CustomerResponse mockCustomerResponse = new CustomerResponse(HttpStatus.NOT_FOUND.value(), "customer records not found");
        ResponseEntity<CustomerResponse> mockResponseEntity = new ResponseEntity<>(mockCustomerResponse, HttpStatus.valueOf(mockCustomerResponse.getErrorCode()));

        Mockito.doReturn(mockResponseEntity).when(customerHelper).createFailureResponse(mockCustomerResponse.getErrorCode(), mockCustomerResponse.getErrorMessage());

        ResponseEntity<?> responseEntity = customerService.getCustomers(null);
        CustomerResponse customerResponse = (CustomerResponse) responseEntity.getBody();

        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(customerResponse);
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
        Assert.assertEquals(404, customerResponse.getErrorCode().intValue());
        Assert.assertEquals("customer records not found", customerResponse.getErrorMessage());
    }

}

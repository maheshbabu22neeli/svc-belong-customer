package com.belong.customer.dao;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Customers;
import com.belong.customer.model.Phone;
import com.belong.customer.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoTest {

    @InjectMocks
    CustomerDao customerDao;

    @Before
    public void init() {
        Map<String, Customer> customersMap = new HashMap<>();
        Customers customers = JsonUtils.jsonFileToModel("/customerData/customers.json", Customers.class);
        customers.getCustomerList().forEach(customer -> {
            customersMap.put(customer.getId(), customer);
        });
        ReflectionTestUtils.setField(customerDao, "customersMap", customersMap);
    }

    // ================================
    // PhoneNumber Test Cases
    // ================================

    @Test
    public void test_getPhoneNumbers_return_all_phoneNumbers() {
        List<Phone> phoneList = customerDao.getPhoneNumbers();

        Assert.assertNotNull(phoneList);
        Assert.assertEquals(15, phoneList.size());
    }

    @Test
    public void test_getPhoneNumbersByCustomerId_return_all_phoneNumbers_of_customer() {
        List<Phone> phoneList = customerDao.getPhoneNumbersByCustomerId("CUS001");

        Assert.assertNotNull(phoneList);
        Assert.assertEquals(3, phoneList.size());
    }

    @Test
    public void test_getPhoneNumbersByCustomerId_return_null_of_unknown_customer() {
        List<Phone> phoneList = customerDao.getPhoneNumbersByCustomerId("CUS000");

        Assert.assertNull(phoneList);
    }

    @Test
    public void test_getCustomerByPhoneNumber_return_customer() {
        Customer customer = customerDao.getCustomerByPhoneNumber("0469002001");

        Assert.assertNotNull(customer);
        Assert.assertEquals("CUS002", customer.getId());
        Assert.assertEquals("Gopi Krishna", customer.getName());
        Assert.assertNotNull(customer.getPhonesList());
    }

    // ================================
    // Customer Test Cases
    // ================================

    @Test
    public void test_getCustomers_return_all_Customers() {

        List<Customer> customerList = customerDao.getCustomers();

        Assert.assertNotNull(customerList);
        Assert.assertEquals(5, customerList.size());
    }

    @Test
    public void test_getCustomers_return_single_customer() {

        Customer customer = customerDao.getCustomerById("CUS002");

        Assert.assertNotNull(customer);
        Assert.assertEquals("CUS002", customer.getId());
        Assert.assertEquals("Gopi Krishna", customer.getName());
        Assert.assertNotNull(customer.getPhonesList());
    }

    @Test
    public void test_saveCustomer() {
        Customer customer = JsonUtils.jsonFileToModel("/mockData/new_customer.json", Customer.class);

        customerDao.saveCustomer(customer);
        Customer addedCustomer = customerDao.getCustomerById("CUS007");

        Assert.assertNotNull(addedCustomer);
        Assert.assertEquals("CUS007", customer.getId());
        Assert.assertEquals("Mahesh Babu 2", customer.getName());
        Assert.assertNotNull(customer.getPhonesList());
    }







}

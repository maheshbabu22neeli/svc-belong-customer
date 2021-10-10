package com.belong.customer.dao;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Customers;
import com.belong.customer.model.Phone;
import com.belong.customer.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class CustomerDao {

    private Map<String, Customer> customersMap;

    private String customerDataFilePath;

    @Autowired
    public CustomerDao(
            @Value("${belong.initial.customer.data.file}") final String customerDataFilePath) {
        this.customerDataFilePath = customerDataFilePath;
    }

    @PostConstruct
    public void loadInitialCustomers() {
        customersMap = new HashMap<>();
        Customers customers = JsonUtils.jsonFileToModel(customerDataFilePath, Customers.class);
        customers.getCustomerList().forEach(customer -> {
            customersMap.put(customer.getId(), customer);
        });
    }

    /*=============================================
     * PhoneNumber Operations
     *=============================================*/
    public List<Phone> getPhoneNumbers() {
        List<Phone> phoneList = new ArrayList<>();
        customersMap.forEach((customerId, customer) -> {
            phoneList.addAll(customer.getPhonesList());
        });
        return phoneList;
    }

    public List<Phone> getPhoneNumbersByCustomerId(final String customerId) {
        Customer customer = customersMap.get(customerId);
        if (ObjectUtils.isEmpty(customer)) {
            return null;
        }
        return customer.getPhonesList();
    }

    public Customer getCustomerByPhoneNumber(final String phoneNumber) {
        for (Map.Entry<String, Customer> customerEntry : customersMap.entrySet()) {
            Customer customer = customerEntry.getValue();
            if (!ObjectUtils.isEmpty(customer)) {
                for (Phone phone : customer.getPhonesList()) {
                    if (phone.getNumber().equals(phoneNumber)) {
                        return customer;
                    }
                }
            }
        }
        return null;
    }

    /*=============================================
     * Customer Operations
     *=============================================*/
    public List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customersMap.forEach((customerId, customer) -> {
            customerList.add(customer);
        });
        return customerList;
    }

    public Customer getCustomerById(final String customerId) {
        return customersMap.get(customerId);
    }

    public void saveCustomer(final Customer customer) {
        customersMap.put(customer.getId(), customer);
    }

}

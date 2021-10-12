package com.belong.customer.dao;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Assuming that CustomerDao is our DB interface class, where it fetches all the DB records from the given DB.
*/

@Service
public class CustomerDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerDao.class);

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
        String customers = JsonUtils.readJsonFile(customerDataFilePath);
        List<Customer> customerList = JsonUtils.stringToModelList(customers, Customer.class);
        customerList.forEach(customer -> customersMap.put(customer.getId(), customer));
        if (CollectionUtils.isEmpty(customersMap)) {
            throw new IllegalArgumentException("Unable to find initial customer data, please check");
        }
    }

    /*=============================================
     * PhoneNumber Operations
     *=============================================*/

    /**
     * @return Actually the below method will retrieve all the phone number from the DB.
     * I would suggest we can build an another method with offsets like FROM and TO.
     * This will increase our performance and memory.
     */
    public List<Phone> getPhoneNumbers() {
        List<Phone> phoneList = new ArrayList<>();
        customersMap.forEach((customerId, customer) ->
                phoneList.addAll(customer.getPhonesList())
        );
        LOGGER.info("Found list of phoneNumbers: {}", phoneList);
        return phoneList;
    }

    /**
     * @param customerId
     * @return
     */
    public List<Phone> getPhoneNumbersByCustomerId(final String customerId) {
        Customer customer = customersMap.get(customerId);
        if (ObjectUtils.isEmpty(customer)) {
            LOGGER.info("Unable to find a customer phoneNumbers with customerId {}", customerId);
            return null;
        }
        LOGGER.info("Found list of phoneNumbers: {} with customerId: {}", customer.getPhonesList(), customerId);
        return customer.getPhonesList();
    }

    /**
     * @param phoneNumber
     * @return If we have an actual DB, we can reduce the iteration by using a select command to retrieve the customer by phoneNumber
     * We have to create an index on this phoneNumber for fast retrieval
     */
    public Customer getCustomerByPhoneNumber(final String phoneNumber) {
        for (Map.Entry<String, Customer> customerEntry : customersMap.entrySet()) {
            Customer customer = customerEntry.getValue();
            for (Phone phone : customer.getPhonesList()) {
                if (phone.getNumber().equals(phoneNumber)) {
                    LOGGER.info("Found a customer: {} with phoneNumber: {}", customer, phoneNumber);
                    return customer;
                }
            }
        }
        LOGGER.info("Unable to find a customer with phoneNumber: {}", phoneNumber);
        return null;
    }

    /*=============================================
     * Customer Operations
     *=============================================*/
    public List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customersMap.forEach((customerId, customer) -> customerList.add(customer));
        LOGGER.info("Found list of Customers: {}", customerList);
        return customerList;
    }

    /**
     * @param customerId
     * @return
     */
    public Customer getCustomerById(final String customerId) {
        return customersMap.get(customerId);
    }

    /**
     * @param customer
     */
    public void saveCustomer(final Customer customer) {
        customersMap.put(customer.getId(), customer);
    }

}

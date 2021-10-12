package com.belong.customer.service;

import com.belong.customer.controller.PhoneNumberController;
import com.belong.customer.dao.CustomerDao;
import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/*
CustomerService class is derived for business logic, where every decision making things will happen
* */

@Service
public class CustomerService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

    private CustomerDao customerDao;

    private CustomerHelper customerHelper;

    @Autowired
    public CustomerService(final CustomerDao customerDao, final CustomerHelper customerHelper) {
        this.customerDao = customerDao;
        this.customerHelper = customerHelper;
    }

    /**
     * @param customerId
     * @return
     */
    public ResponseEntity<?> getCustomers(final String customerId) {

        ResponseEntity<?> responseEntity;
        if (!StringUtils.hasLength(customerId)) {
            List<Customer> customerList = customerDao.getCustomers();

            if (CollectionUtils.isEmpty(customerList)) {
                responseEntity = customerHelper.createFailureResponse(
                        HttpStatus.NOT_FOUND.value(), "customer records not found");
            } else {
                responseEntity = customerHelper.createSuccessResponse(customerList, HttpStatus.OK);
            }

        } else {
            Customer customer = customerDao.getCustomerById(customerId);

            if (ObjectUtils.isEmpty(customer)) {
                responseEntity = customerHelper.createFailureResponse(
                        HttpStatus.NOT_FOUND.value(), "customer records not found");
            } else {
                responseEntity = customerHelper.createSuccessResponse(customer, HttpStatus.OK);
            }
        }

        LOGGER.info("GET Customer, Response sent: {}", responseEntity.toString());
        return responseEntity;
    }

}

package com.belong.customer.service;

import com.belong.customer.controller.PhoneNumberController;
import com.belong.customer.dao.CustomerDao;
import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

    public ResponseEntity<?> getCustomers(final String customerId) {

        List<Customer> customerList;
        if (customerId == null)
            customerList = customerDao.getCustomers();
        else
            customerList = customerDao.getCustomersById(customerId);

        ResponseEntity<?> responseEntity;
        if (!CollectionUtils.isEmpty(customerList)) {
            responseEntity = customerHelper.createSuccessResponse(customerList, HttpStatus.OK);
            LOGGER.info("GET Customer, Response sent: {}", responseEntity.toString());
        } else {
            responseEntity = customerHelper.createFailureResponse(
                    HttpStatus.NOT_FOUND.value(), "customerId Not Found");
            LOGGER.error("GET Customer, Response sent: {}", responseEntity.toString());
        }

        return responseEntity;
    }

}

package com.belong.customer.service;

import com.belong.customer.controller.PhoneNumberController;
import com.belong.customer.dao.CustomerDao;
import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
import com.belong.customer.model.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PhoneNumberService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

    private CustomerDao customerDao;

    private PhoneNumberHelper phoneNumberHelper;

    private CustomerHelper customerHelper;

    @Autowired
    public PhoneNumberService(final CustomerDao customerDao,
                              final PhoneNumberHelper phoneNumberHelper,
                              final CustomerHelper customerHelper) {
        this.customerDao = customerDao;
        this.phoneNumberHelper = phoneNumberHelper;
        this.customerHelper = customerHelper;
    }

    public ResponseEntity<Object> getPhoneNumbers(final String customerId) {
        LOGGER.info("Begin getPhoneNumbers PhoneNumberService");

        List<Phone> phoneList;
        ResponseEntity<Object> responseEntity;

        // If Customer ID is NULL
        if (!StringUtils.hasLength(customerId)) {
            phoneList = customerDao.getPhoneNumbers();
        } else {
            // Customer ID is not NULL
            phoneList = customerDao.getPhoneNumbersByCustomerId(customerId);
        }

        if (!CollectionUtils.isEmpty(phoneList)) {
            // Creates Success PhoneNumberResponse
            responseEntity = phoneNumberHelper.createSuccessResponse(phoneList, HttpStatus.OK);
            LOGGER.info("GET PhoneNumber, Response sent: {}", responseEntity.toString());
        } else {
            // Creates Error PhoneNumberResponse
            responseEntity = phoneNumberHelper.createFailureResponse(
                    HttpStatus.NOT_FOUND.value(), "customerId Not Found");
            LOGGER.error("GET PhoneNumber, Response sent: {}", responseEntity.toString());
        }

        return responseEntity;
    }

    public ResponseEntity<Object> activatePhoneNumber(
            final String phoneNumber, final String state) {

        ResponseEntity<Object> responseEntity;

        Customer customer = customerDao.getCustomerByPhoneNumber(phoneNumber);
        if (ObjectUtils.isEmpty(customer)) {
            responseEntity = phoneNumberHelper.createFailureResponse(
                    HttpStatus.NOT_FOUND.value(), "phone number not found, please check");
            LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
            return responseEntity;
        }

        List<Phone> updatedPhoneList = new ArrayList<>();
        for (Phone phone : customer.getPhonesList()) {
            if (phone.getNumber().equals(phoneNumber)) {
                phone.setState(state);
            }
            updatedPhoneList.add(phone);
        }
        customer.setPhonesList(updatedPhoneList);

        customerDao.saveCustomer(customer);

        responseEntity = phoneNumberHelper.createSuccessResponse(customer, HttpStatus.OK);

        LOGGER.info("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
        return responseEntity;
    }
}

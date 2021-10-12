package com.belong.customer.service;

import com.belong.customer.controller.PhoneNumberController;
import com.belong.customer.dao.CustomerDao;
import com.belong.customer.enums.PhoneNumberStateEnum;
import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
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

/*
PhoneNumberService class is derived for business logic, where every decision making things will happen
* */

@Service
public class PhoneNumberService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

    private CustomerDao customerDao;

    private PhoneNumberHelper phoneNumberHelper;

    /**
     * @param customerDao
     * @param phoneNumberHelper
     */
    @Autowired
    public PhoneNumberService(final CustomerDao customerDao,
                              final PhoneNumberHelper phoneNumberHelper) {
        this.customerDao = customerDao;
        this.phoneNumberHelper = phoneNumberHelper;
    }

    public ResponseEntity<?> getPhoneNumbers(final String customerId) {

        List<Phone> phoneList;
        ResponseEntity<?> responseEntity;

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
        } else {
            // Creates Error PhoneNumberResponse
            responseEntity = phoneNumberHelper.createFailureResponse(
                    HttpStatus.NOT_FOUND.value(), "customerId Not Found");
        }

        LOGGER.info("GET PhoneNumber, Response sent: {}", responseEntity.toString());
        return responseEntity;
    }

    /**
     * @param phoneNumber
     * @param state
     * @return
     *
     */
    public ResponseEntity<?> activatePhoneNumber(
            final String phoneNumber, final String state) {

        ResponseEntity<?> responseEntity;

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
                if (phone.getState().equals(PhoneNumberStateEnum.ACTIVE.toString())) {
                    responseEntity = phoneNumberHelper.createFailureResponse(
                            HttpStatus.BAD_REQUEST.value(), "phone number already in active state");
                    LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                    return responseEntity;
                }
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

package com.belong.customer.controller;

import com.belong.customer.enums.PhoneNumberStateEnum;
import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.State;
import com.belong.customer.service.PhoneNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/*PhoneNumberController is an api interface for phoneNumber related functionalities
It exposed an end-point
        1. /v1/belong/phonenumbers
            Which retrieves all the phonenumbers details
        2. /v1/belong/phonenumbers?customerId={{customerId}}
            Which retrieves a customer phonenumbers details based on customerId
*/

@RestController
@RequestMapping("${service.basePath}")
public class PhoneNumberController {

    public static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberController.class);

    private PhoneNumberService phoneNumberService;

    private PhoneNumberHelper phoneNumberHelper;

    @Autowired
    public PhoneNumberController(final PhoneNumberService phoneNumberService,
                                 final PhoneNumberHelper phoneNumberHelper) {
        this.phoneNumberService = phoneNumberService;
        this.phoneNumberHelper = phoneNumberHelper;
    }

    /**
     * @param customerId
     * @return
     */
    @GetMapping(path = "/phonenumbers")
    public ResponseEntity<?> getPhoneNumbers(
            @RequestParam(required = false) final String customerId) {
        LOGGER.info("GET PhoneNumber, Request Received, where customerId: {}", customerId);

        ResponseEntity<?> responseEntity;
        try {

            if (StringUtils.hasLength(customerId) && !Pattern.matches("CUS[0-9]{3}", customerId)) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(), "invalid customerId, required format is CUS[0-9]{3}");
                LOGGER.error("GET PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            responseEntity = phoneNumberService.getPhoneNumbers(customerId);
            return responseEntity;

        } catch (final Exception exception) {
            exception.printStackTrace();

            responseEntity = phoneNumberHelper.createFailureResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong, please contact team");

            LOGGER.error("GET PhoneNumber, Response sent: {}", responseEntity.toString());
            return responseEntity;
        }
    }

    /**
     * @param phoneNumber
     * @param state
     * @return
     */
    @PatchMapping(path = "/phoneNumbers/{phoneNumber}")
    public ResponseEntity<?> activatePhoneNumber(
            @PathVariable String phoneNumber, @RequestBody State state) {
        LOGGER.info("Activate PhoneNumber, Request Received, where phoneNumber: {}, body: {}",
                phoneNumber, state);

        ResponseEntity<?> responseEntity;
        try {
            if (!StringUtils.hasLength(phoneNumber)) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(), "phoneNumber cannot be Empty");
                LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            if (StringUtils.hasLength(phoneNumber) && !Pattern.matches("[0-9]{10}", phoneNumber)) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(), "invalid phoneNumber, required format [0-9]{10}");
                LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            if (ObjectUtils.isEmpty(state)) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(), "activatePhoneNumber body cannot be Empty");
                LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            if (!StringUtils.hasLength(state.getState())) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(), "phone state cannot be Empty");
                LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            if (ObjectUtils.isEmpty(PhoneNumberStateEnum.getPhoneNumberStateEnumByState(state.getState()))) {
                responseEntity = phoneNumberHelper.createFailureResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "invalid phone state, require ENUM are Active or InActive");
                LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
                return responseEntity;
            }

            responseEntity = phoneNumberService.activatePhoneNumber(phoneNumber, state.getState());
            return responseEntity;

        } catch (final Exception exception) {
            exception.printStackTrace();

            responseEntity = phoneNumberHelper.createFailureResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong, please contact team");

            LOGGER.error("Activate PhoneNumber, Response sent: {}", responseEntity.toString());
            return responseEntity;
        }

    }

}

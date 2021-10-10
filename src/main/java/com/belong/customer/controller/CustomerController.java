package com.belong.customer.controller;

import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.helper.PhoneNumberHelper;
import com.belong.customer.model.CustomerResponse;
import com.belong.customer.model.PhoneNumberResponse;
import com.belong.customer.service.CustomerService;
import com.github.fge.jsonpatch.JsonPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${service.basePath}")
public class CustomerController {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    private CustomerHelper customerHelper;

    @Autowired
    public CustomerController(final CustomerService customerService,
                              final CustomerHelper customerHelper) {
        this.customerService = customerService;
        this.customerHelper = customerHelper;
    }

    @GetMapping("/customers")
    public ResponseEntity<Object> getCustomer(@RequestParam(required = false) final String customerId) {
        LOGGER.info("GET Customer, Request Received, where customerId: {}", customerId);

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = customerService.getCustomers(customerId);
            return responseEntity;
        } catch (final Exception exception) {
            exception.printStackTrace();

            responseEntity = customerHelper.createFailureResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong, please contact team");

            LOGGER.error("GET Customer, Response sent: {}", responseEntity.toString());
            return responseEntity;
        }
    }

    @PatchMapping(path = "/customers/{customerId}")
    public ResponseEntity<Object> activatePhoneNumber(
            @PathVariable final String customerId,
            @RequestBody final JsonPatch jsonPatch) {
        LOGGER.info("PATCH ActivatePhoneNumber, Request Received, where customerId: {}", customerId);

        ResponseEntity<Object> responseEntity;
        try {

            responseEntity = customerService.activatePhoneNumber(customerId, jsonPatch);
            return responseEntity;

        } catch (final Exception exception) {
            exception.printStackTrace();
            responseEntity = customerHelper.createFailureResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong, please contact team");

            LOGGER.error("PATCH ActivatePhoneNumber, Response sent: {}", responseEntity.toString());
            return responseEntity;
        }
    }

}

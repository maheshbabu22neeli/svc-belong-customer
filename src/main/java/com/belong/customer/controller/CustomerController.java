package com.belong.customer.controller;

import com.belong.customer.helper.CustomerHelper;
import com.belong.customer.service.CustomerService;
import com.github.fge.jsonpatch.JsonPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<?> getCustomers(@RequestParam(required = false) final String customerId) {
        LOGGER.info("GET Customer, Request Received, where customerId: {}", customerId);

        ResponseEntity<?> responseEntity;
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

}

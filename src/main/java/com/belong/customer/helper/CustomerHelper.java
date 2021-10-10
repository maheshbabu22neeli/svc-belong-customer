package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class CustomerHelper {

    public boolean isValidCustomerId(final String customerId) {
        return Pattern.matches("CUS[0-9]{3}", customerId);
    }

    public ResponseEntity<Object> createSuccessResponse(final List<Customer> customerList, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new CustomerResponse(customerList), httpStatus);
    }

    public ResponseEntity<Object> createSuccessResponse(final Customer customer, final HttpStatus httpStatus) {
        return new ResponseEntity<>(customer, httpStatus);
    }

    public ResponseEntity<Object> createFailureResponse(final int errorCode, final String errorMessage) {
        return new ResponseEntity<>(new CustomerResponse(errorCode, errorMessage), HttpStatus.valueOf(errorCode));
    }

}

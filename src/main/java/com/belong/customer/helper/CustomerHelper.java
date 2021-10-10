package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerHelper {

    public ResponseEntity<?> createSuccessResponse(final List<Customer> customerList, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new CustomerResponse(customerList), httpStatus);
    }

    public ResponseEntity<?> createFailureResponse(final int errorCode, final String errorMessage) {
        return new ResponseEntity<>(new CustomerResponse(errorCode, errorMessage), HttpStatus.valueOf(errorCode));
    }

}

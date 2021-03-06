package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/*
Created CustomerHelper class to code any generic logic for customers which can be used on the customer service class
*/

@Component
public class CustomerHelper {

    /**
     * @param customerList
     * @param httpStatus
     * @return
     */
    public ResponseEntity<Object> createSuccessResponse(final List<Customer> customerList, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new CustomerResponse(customerList), httpStatus);
    }

    /**
     * @param customer
     * @param httpStatus
     * @return
     */
    public ResponseEntity<Object> createSuccessResponse(final Customer customer, final HttpStatus httpStatus) {
        return new ResponseEntity<>(customer, httpStatus);
    }

    /**
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public ResponseEntity<Object> createFailureResponse(final int errorCode, final String errorMessage) {
        return new ResponseEntity<>(new CustomerResponse(errorCode, errorMessage), HttpStatus.valueOf(errorCode));
    }


}

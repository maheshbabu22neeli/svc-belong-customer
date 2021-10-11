package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/*
Created PhoneNumberHelper class to code any generic logic for PhoneNumbers which can be used on the PhoneNumber service class
*/

@Component
public class PhoneNumberHelper {

    /**
     * @param phoneList
     * @param httpStatus
     * @return
     */
    public ResponseEntity<?> createSuccessResponse(final List<Phone> phoneList, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new PhoneNumberResponse(phoneList), httpStatus);
    }

    /**
     * @param customer
     * @param httpStatus
     * @return
     */
    public ResponseEntity<?> createSuccessResponse(final Customer customer, final HttpStatus httpStatus) {
        return new ResponseEntity<>(customer, httpStatus);
    }

    /**
     * @param errorCode
     * @param errorMessage
     * @return
     */
    public ResponseEntity<?> createFailureResponse(final int errorCode, final String errorMessage) {
        return new ResponseEntity<>(new PhoneNumberResponse(errorCode, errorMessage), HttpStatus.valueOf(errorCode));
    }


}

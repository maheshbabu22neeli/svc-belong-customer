package com.belong.customer.helper;

import com.belong.customer.model.Customer;
import com.belong.customer.model.Phone;
import com.belong.customer.model.PhoneNumberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneNumberHelper {

    public ResponseEntity<?> createSuccessResponse(final List<Phone> phoneList, final HttpStatus httpStatus) {
        return new ResponseEntity<>(new PhoneNumberResponse(phoneList), httpStatus);
    }

    public ResponseEntity<?> createSuccessResponse(final Customer customer, final HttpStatus httpStatus) {
        return new ResponseEntity<>(customer, httpStatus);
    }

    public ResponseEntity<?> createFailureResponse(int errorCode, String errorMessage) {
        return new ResponseEntity<>(new PhoneNumberResponse(errorCode, errorMessage), HttpStatus.valueOf(errorCode));
    }


}

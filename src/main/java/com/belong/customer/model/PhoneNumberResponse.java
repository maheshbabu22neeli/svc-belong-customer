package com.belong.customer.model;

import com.belong.customer.model.base.AbstractModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumberResponse extends AbstractModel {

    private Integer records;

    @JsonProperty("phone")
    private List<Phone> phoneList;

    private Integer errorCode;

    private String errorMessage;

    private Customer customer;

    public PhoneNumberResponse(final List<Phone> phoneList) {
        this.phoneList = phoneList;
        this.records = phoneList.size();
    }

    public PhoneNumberResponse(final Integer errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public PhoneNumberResponse(final Customer customer) {
        this.customer = customer;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(final Integer records) {
        this.records = records;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(final List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }
}

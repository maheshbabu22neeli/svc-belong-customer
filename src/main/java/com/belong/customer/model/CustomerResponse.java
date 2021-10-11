package com.belong.customer.model;

import com.belong.customer.model.base.AbstractModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponse extends AbstractModel {

    private Integer records;

    @JsonProperty("customer")
    private List<Customer> customerList;

    private Integer errorCode;

    private String errorMessage;

    public CustomerResponse(final List<Customer> customerList) {
        this.customerList = customerList;
        this.records = customerList.size();
    }

    public CustomerResponse(final Integer errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(final Integer records) {
        this.records = records;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(final List<Customer> customerList) {
        this.customerList = customerList;
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
}

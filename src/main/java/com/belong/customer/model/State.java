package com.belong.customer.model;

import com.belong.customer.model.base.AbstractModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class State extends AbstractModel {

    private String state;

    public State() {
    }

    public State(final String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }
}

package com.belong.customer.enums;

import java.util.HashMap;
import java.util.Map;

public enum PhoneNumberStateEnum {
    ACTIVE("Active"),
    IN_ACTIVE("InActive");

    private static Map<String, PhoneNumberStateEnum> phoneNumberStateEnumMap;

    private final String value;

    PhoneNumberStateEnum(final String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    static {
        createPhoneNumberEnumMap();
    }

    private static void createPhoneNumberEnumMap() {
        phoneNumberStateEnumMap = new HashMap<>();
        for (PhoneNumberStateEnum phoneNumberStateEnum : PhoneNumberStateEnum.values()) {
            phoneNumberStateEnumMap.put(phoneNumberStateEnum.value, phoneNumberStateEnum);
        }
    }

    public static PhoneNumberStateEnum getPhoneNumberStateEnumByState(final String state) {
        return phoneNumberStateEnumMap.get(state);
    }

}

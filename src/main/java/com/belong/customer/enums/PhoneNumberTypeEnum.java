package com.belong.customer.enums;

import java.util.HashMap;
import java.util.Map;

public enum PhoneNumberTypeEnum {
    OFFICE("Office"),
    HOME("Home"),
    MOBILE("Mobile");

    private static Map<String, PhoneNumberTypeEnum> phoneNumberTypeEnumMap;

    private final String value;

    PhoneNumberTypeEnum(final String value) {
        this.value = value;
    }

    static {
        createPhoneNumberTypeEnumMap();
    }

    private static void createPhoneNumberTypeEnumMap() {
        phoneNumberTypeEnumMap = new HashMap<>();
        for (PhoneNumberTypeEnum phoneNumberTypeEnum : PhoneNumberTypeEnum.values()) {
            phoneNumberTypeEnumMap.put(phoneNumberTypeEnum.value, phoneNumberTypeEnum);
        }
    }

    public PhoneNumberTypeEnum getPhoneNumberTypeEnumByType(final String type) {
        return phoneNumberTypeEnumMap.get(type);
    }

}

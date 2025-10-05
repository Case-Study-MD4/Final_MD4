package com.example.case_study_module_4.common;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PROCESSING(0),
    COMPLETED(1),
    CANCELLED(2);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code: " + code);
    }
}



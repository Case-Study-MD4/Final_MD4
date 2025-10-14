package com.example.case_study_module_4.common;

import lombok.Getter;

@Getter
public enum
OrderStatus {

    PENDING(0),
    PROCESSING(1),
    COMPLETED(2),
    CANCELLED(3);

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



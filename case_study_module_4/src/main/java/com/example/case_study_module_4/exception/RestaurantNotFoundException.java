package com.example.case_study_module_4.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(Long message){
        super(String.valueOf(message));
    }
}

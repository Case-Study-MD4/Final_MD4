package com.example.case_study_module_4.exception;

public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(Long message){
        super(String.valueOf(message));
    }
}

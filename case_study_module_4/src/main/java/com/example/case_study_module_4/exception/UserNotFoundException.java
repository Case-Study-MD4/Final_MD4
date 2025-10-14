package com.example.case_study_module_4.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long message){
        super(String.valueOf(message));
    }
}

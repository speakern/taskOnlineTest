package com.interview.onlineTest.exceptions;


public class UserExistsException extends RuntimeException {
    public UserExistsException(String userName) {
        super("Пользователь с логином " + userName + " уже существует");
    }
}
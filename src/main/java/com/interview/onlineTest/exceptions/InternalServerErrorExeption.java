package com.interview.onlineTest.exceptions;


public class InternalServerErrorExeption extends RuntimeException {
    public InternalServerErrorExeption() {
        super("Внутренняя ошибка сервера. Попробуйте повторить позденее");
    }
}
package com.interview.onlineTest.exceptions;

public class NotCorrectNumberOfAnswerExeption extends RuntimeException {
    public NotCorrectNumberOfAnswerExeption(int idAnswer) {
        super("Answer ID is incorrect - " + idAnswer + " Enter a number between 0 - 3");
    }
}

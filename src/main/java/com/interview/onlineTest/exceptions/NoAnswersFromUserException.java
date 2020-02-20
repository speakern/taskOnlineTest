package com.interview.onlineTest.exceptions;

public class NoAnswersFromUserException extends RuntimeException {
    public NoAnswersFromUserException() {
        super("Не ответов ни на один вопрос");
    }
}
package com.interview.onlineTest.exceptions;


import com.interview.onlineTest.controller.QueryController;

public class NotCorrectCountAnswersException extends RuntimeException {
    public NotCorrectCountAnswersException(int countAnswers, int countRightAnswers) {
        super("Неправильное количество вариантов ответов -" + countAnswers + " должно быть " + countRightAnswers);
    }
}

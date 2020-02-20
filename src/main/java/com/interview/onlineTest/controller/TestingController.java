package com.interview.onlineTest.controller;

import com.interview.onlineTest.dto.Pair;
import com.interview.onlineTest.dto.Response;
import com.interview.onlineTest.dto.User;
import com.interview.onlineTest.exceptions.NoAnswersFromUserException;
import com.interview.onlineTest.repository.TestingRepository;
import com.interview.onlineTest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class TestingController {

    @Autowired
    TestingRepository testingRepository;

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(TestingController.class);

    @GetMapping(value ="/answers", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Pair> getAllAnswers(){

        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return testingRepository.getUserAnswers(user);
    }

    @PostMapping("/answers")
    public ResponseEntity<Response> userAnswers(@RequestBody @Valid ArrayList<Pair> queryAnswerPairs) {

        queryAnswerPairs.removeIf(e -> e.getAnswer().equals(""));
        if (queryAnswerPairs.size() > 0) {
            testingRepository.deleteUserAnswers(currentUserID());
            logger.info("Тест пройден успешно.");
            return ResponseEntity.ok().body(new Response("Test passed. Number of correct answers: " +
                    addUserAnswersToDB(queryAnswerPairs), ""));
        } else {
            logger.warn("Тест не пройден. Нет ответов в сообщении.");
            throw new NoAnswersFromUserException();
        }
    }

    private int currentUserID() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    private int addUserAnswersToDB(ArrayList<Pair> queryAnswerPairs) {
        Map<Integer, String> rightAnswers = testingRepository.getRightAnswers();
        int countCorrectAnswers = 0;
        for (Pair pair : queryAnswerPairs) {
            if (rightAnswers.get(pair.getQueryId()) != null) {
                int isCorrect = 0;
                if (rightAnswers.get(pair.getQueryId()).equals(pair.getAnswer())) {
                    countCorrectAnswers++;
                    isCorrect = 1;
                }
                testingRepository.addUserAnswer(currentUserID(), pair.getQueryId(), pair.getAnswer(), isCorrect);
            }
        }
        logger.info("Ответы записаны в БД");
        return countCorrectAnswers;
    }
}


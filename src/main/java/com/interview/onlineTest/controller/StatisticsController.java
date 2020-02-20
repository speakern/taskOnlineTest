package com.interview.onlineTest.controller;

import com.interview.onlineTest.dto.Response;
import com.interview.onlineTest.repository.StatisticsRepository;
import com.interview.onlineTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StatisticsController {

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/countUsers")
    public ResponseEntity<Response> getCountUsers(){
        return ResponseEntity.ok().body(new Response( statisticsRepository.getCountUsers(),
                "Сколько всего пользователей зарегистрировано в системе"));
    }

    @GetMapping("/countUsersTested")
    public ResponseEntity<Response> getCountUsersTested(){
        return ResponseEntity.ok().body(new Response(Integer.toString(statisticsRepository.getCountAllTestedUsers()),
                "Сколько пользователей прошли тестирование"));
    }

    @GetMapping("/countUsersTestedAllQuestions")
    public ResponseEntity<Response> getCountUsersTestedAllQuestions(){
        return ResponseEntity.ok().body(new Response( statisticsRepository.getCountUsersAnsweredAllQuestions(),
                "Сколько пользователей ответили на все вопросы тестирования"));
    }

    @GetMapping("/countUsersTestedAllQuestionsSuccess")
    public ResponseEntity<Response> getCountUsersTestedAllQuestionsSuccess(){
        return ResponseEntity.ok().body(new Response( statisticsRepository.getCountUsersTestedAllQuestionsSuccess(),
                "Сколько пользователей ответили на все вопросы тестирования правильно"));
    }

    @GetMapping("/countRightAnswers")
    public ResponseEntity<Response> getСountRightAnswers(){
        return ResponseEntity.ok().body(new Response(
                "Процент прохождения: " + String.format("%.2f", rightAnswersCurrentUser() * 100.0/countAllQueries())  +
                         "%,  " + rightAnswersCurrentUser() + " правильных ответов из " + countAllQueries(),
                "Процент прохождения тестирования текущего пользователя (сколько правильных ответов он дал)"));
    }

    @GetMapping("/countUsersTestedWorseCurrent")
    public ResponseEntity<Response> getСountUsersTestedWorseCurrent(){

        int countUsersTestedWorseCurrent = statisticsRepository.getСountUsersTestedWorseCurrent(rightAnswersCurrentUser());
        return ResponseEntity.ok().body(new Response(
                "Процент пользователей: " + String.format("%.2f", countUsersTestedWorseCurrent * 100.0/countTestedUsers())+
                        "%,  " + countUsersTestedWorseCurrent + " пользователей из " + countTestedUsers(),
                "Сколько процентов пользователей справилось с тестированием хуже текущего пользователя"));
    }

    @GetMapping("/countUsersTestedBetterCurrent")
    public ResponseEntity<Response> getСountUsersTestedBetterCurrent(){

        int countUsersTestedBetterCurrent = statisticsRepository.getСountUsersTestedBetterCurrent(rightAnswersCurrentUser());
        return ResponseEntity.ok().body(new Response(
                "Процент пользователей: " + String.format("%.2f", countUsersTestedBetterCurrent * 100.0/countTestedUsers()) +
                        "%,  " + countUsersTestedBetterCurrent + " пользователей из " + countTestedUsers(),
                "Сколько процентов пользователей справилось с тестированием лучше текущего пользователя"));
    }

    @GetMapping("/countUsersTestedEqualCurrent")
    public ResponseEntity<Response> getСountUsersTestedEqualCurrent(){

        int countUsersTestedEqualCurrent = statisticsRepository.getСountUsersTestedEqualCurrent(rightAnswersCurrentUser());
        return ResponseEntity.ok().body(new Response(
                "Процент пользователей: " + String.format("%.2f",countUsersTestedEqualCurrent * 100.0/countTestedUsers()) +
                        "%,  " + countUsersTestedEqualCurrent + " пользователей из " + countTestedUsers(),
                "Сколько процентов пользователей справилось с тестированием также как и текущий пользователь"));
    }


    private int currentUserID () {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    private int countAllQueries () {
        return statisticsRepository.countQueries();
    }

    private int rightAnswersCurrentUser() {
        return statisticsRepository.countRightAnswersUser(currentUserID());
    }

    private int countTestedUsers() {
        return statisticsRepository.getCountAllTestedUsers();
    }
}
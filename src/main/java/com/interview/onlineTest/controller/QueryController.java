package com.interview.onlineTest.controller;

import com.interview.onlineTest.dto.Query;
import com.interview.onlineTest.dto.Response;
import com.interview.onlineTest.exceptions.InternalServerErrorExeption;
import com.interview.onlineTest.exceptions.NotCorrectCountAnswersException;
import com.interview.onlineTest.exceptions.NotCorrectNumberOfAnswerExeption;
import com.interview.onlineTest.repository.QueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RestController
public class QueryController {

    @Autowired
    QueryRepository queryRepository;

    Logger logger = LoggerFactory.getLogger(QueryController.class);

    public static final int COUNT_RIGHT_ANSWERS_FOR_TYPE_1 = 4;

    @GetMapping(value = "/queries", produces = APPLICATION_JSON_UTF8_VALUE)
    public List<Query> getAllQueries(){
        return queryRepository.getAllQueries(false);
    }

    @PostMapping("/queries")
    public ResponseEntity<Response> addQuery(@RequestBody @Valid Query query) {

        query.getAnswers().removeIf(e -> e.equals(""));
        if (query.getType() == 1) {
            if ((query.getRight_answer() > 3) || (query.getRight_answer() < 0)) {
                logger.error("Номер ответа неверен.");
                throw new NotCorrectNumberOfAnswerExeption(query.getRight_answer());
            }
            if (query.getAnswers().size() != COUNT_RIGHT_ANSWERS_FOR_TYPE_1) {
                logger.error("Количесто ответов меньше {}", COUNT_RIGHT_ANSWERS_FOR_TYPE_1);
                throw new NotCorrectCountAnswersException(query.getAnswers().size(), COUNT_RIGHT_ANSWERS_FOR_TYPE_1);
            }

        } else if ((query.getType() == 2) && (query.getAnswers().size() == 0)) {
            logger.error("Ошибка добавления вопроса 2-го типа. Ответа нет в сообщении");
            throw new NotCorrectCountAnswersException(query.getAnswers().size(), 1);
        }

        if( queryRepository.addQuery(query) >= 1){
            logger.info("Вопрос успешно добавлен");
            return ResponseEntity.ok().body(new Response("Query Added Successfully", ""));
        } else {
            logger.error("Ошибка добавления запроса");
            throw new InternalServerErrorExeption();
        }
    }

}

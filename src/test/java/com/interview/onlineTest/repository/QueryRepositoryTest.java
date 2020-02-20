package com.interview.onlineTest.repository;

import com.interview.onlineTest.dto.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by leha on 05.02.20.
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryRepositoryTest {

    @Autowired
    QueryRepository queryRepository;

    @Test
    public void testGetAllQueries() {
       List<Query> queries  =  queryRepository.getAllQueries(true);
       assertEquals(queries.get(0).getAnswers().get(0), "красный");
    }


    @Test
    public void testGetAnswers() {
        List<String> answers  =  queryRepository.getAnswers(1);
        assertEquals(answers.get(0), "красный");
    }

    @Test
    public void testAddAnswer() {
        queryRepository.addAnswer("фиолетовый", 4, 1);
        List<String> answers  =  queryRepository.getAnswers(1);
        assertEquals(answers.get(4), "фиолетовый");
    }

    @Test
    public void testAddQuery() {
        Query query = new Query(1,"qwqwqw ?", 1, 2, new ArrayList<String>(Arrays.asList("ans1", "ans2", "ans3", "ans4")));
        queryRepository.addQuery(query);

        List<Query> queries  =  queryRepository.getAllQueries(true);
        assertEquals(queries.get(5).getText(), "qwqwqw ?");
    }

}
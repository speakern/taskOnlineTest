package com.interview.onlineTest.repository;

import com.interview.onlineTest.dto.Pair;
import com.interview.onlineTest.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by leha on 15.02.20.
 */
@SpringBootTest
public class TestingRepositoryTest {

    @Autowired
    TestingRepository testingRepository;

    @Test
    public void testGetAnswers() {
        User user = new User(1, "name", "password", true);
        List<Pair> pairs  =  testingRepository.getUserAnswers(user);

        assertEquals(pairs.get(0).getAnswer(), "оранжевый");
    }

    @Test
    public void testGetRightAnswers() {
        Map<Integer, String> rightAnswers  = testingRepository.getRightAnswers();

        assertEquals(rightAnswers.get(1), "оранжевый");
        assertEquals(rightAnswers.get(2), "слон");
    }
}

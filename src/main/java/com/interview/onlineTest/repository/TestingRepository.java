package com.interview.onlineTest.repository;

import com.interview.onlineTest.dto.Pair;
import com.interview.onlineTest.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestingRepository {
    @Autowired
    JdbcTemplate template;

    private static final String RIGHT_ANSWER_QUERY = "select q.id, q.type, right_answer, a.text  from Queries q join Answers a on q.id = a.query_id and q.right_answer = a.query_num";
    public Map<Integer, String> getRightAnswers(){

        Map<Integer, String> rightAnswers = new HashMap<>();

        template.query(RIGHT_ANSWER_QUERY,(result,rowNum)->
                rightAnswers.put(result.getInt("id"), result.getString("text")));
        return rightAnswers;
    }

    private static final String DELETE_USER_ANSWERS_QUERY = "DELETE User_Answers where user_id = ?";
    public void deleteUserAnswers(int user_id) {
        template.update(DELETE_USER_ANSWERS_QUERY, user_id);
    }

    private static final String ADD_USER_ANSWER_QUERY = "INSERT INTO User_Answers (user_id, query_id, answer, is_correct) VALUES (?,?,?,?)";

    public synchronized void addUserAnswer(int user_id, int query_id, String answer, int is_correct) {
            template.update(ADD_USER_ANSWER_QUERY, user_id, query_id, answer, is_correct);
    }

    private static final String USER_ANSWERS_QUERY =
            "select q.id as id, q.text as q_text, answer from user_answers ua join queries q on ua.query_id = q.ID  where user_id = ?";
    public List<Pair> getUserAnswers(User user){

        List<Pair> pairs = template.query(USER_ANSWERS_QUERY,(result,rowNum)-> new Pair(
                result.getInt("id"),
                result.getString("q_text"),
                result.getString("answer")
        ), user.getId());
        return pairs;
    }
}
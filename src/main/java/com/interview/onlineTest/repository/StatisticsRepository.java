package com.interview.onlineTest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class StatisticsRepository {
    @Autowired
    JdbcTemplate template;

    private static final String COUNT_USERS_QUERY = "select count(*) FROM USERS";
    public String getCountUsers() {
        return template.queryForObject(COUNT_USERS_QUERY, String.class);
    }

    private static final String COUNT_ALL_TESTED_USERS_QUERY = "select count(*) as count from (SELECT distinct user_id FROM USER_ANSWERS)";
    public Integer getCountAllTestedUsers() {
        return template.queryForObject(COUNT_ALL_TESTED_USERS_QUERY, Integer.class);
    }

    private static final String COUNT_USERS_ANSWERED_ALL_QUESTIOS_QUERY =
            "select count(*) from (SELECT user_id, count(*) FROM USER_ANSWERS group by user_id having count(*) = (select count(*) from queries))";
    public String getCountUsersAnsweredAllQuestions() {
        return template.queryForObject(COUNT_USERS_ANSWERED_ALL_QUESTIOS_QUERY, String.class);
    }

    private static final String COUNT_TESTED_USERS_ALL_QUESTIOS_SUCCESS_QUERY =
            "select count(*) from (SELECT user_id, count(*) FROM USER_ANSWERS where is_correct = 1group by user_id having count(*) = (select count(*) from queries))";
    public String getCountUsersTestedAllQuestionsSuccess() {
        return template.queryForObject(COUNT_TESTED_USERS_ALL_QUESTIOS_SUCCESS_QUERY, String.class);
    }

    private static final String COUNT_QUERIES_QUERY = "select count(*) FROM queries";
    public Integer countQueries() {
        return template.queryForObject(COUNT_QUERIES_QUERY, Integer.class);
    }

    private static final String COUNT_RIGHT_ANSWER_FOR_USER_QUERY = "select count(*) FROM USER_ANSWERS where is_correct = 1 and user_id = ?";
    public Integer countRightAnswersUser(int user_id) {
        return template.queryForObject(COUNT_RIGHT_ANSWER_FOR_USER_QUERY, Integer.class, user_id);
    }

    private static final String COUNT_USERS_TESTED_WORSE_OR_BETTER_CURRENT_QUERY =
                "select  count(*)  from (" +
                " select user_id, count(*) as count FROM USER_ANSWERS where is_correct = 1  group by user_id " +
                " ) t right join (" +
                " select distinct user_id from USER_ANSWERS" +
                " ) p on t.user_id = p.user_id " +
                " where COALESCE(count, 0)" ;

    public Integer getСountUsersTestedWorseCurrent(int rightAnswersCurrentUser) {
        return template.queryForObject(COUNT_USERS_TESTED_WORSE_OR_BETTER_CURRENT_QUERY + " < ?", Integer.class, rightAnswersCurrentUser);
    }

    public Integer getСountUsersTestedBetterCurrent(int rightAnswersCurrentUser) {
        return template.queryForObject(COUNT_USERS_TESTED_WORSE_OR_BETTER_CURRENT_QUERY + " > ?", Integer.class, rightAnswersCurrentUser);
    }

    public Integer getСountUsersTestedEqualCurrent(int rightAnswersCurrentUser) {
        return template.queryForObject(COUNT_USERS_TESTED_WORSE_OR_BETTER_CURRENT_QUERY + " = ?", Integer.class, rightAnswersCurrentUser) -1; //самого себя вычитаем
    }
}
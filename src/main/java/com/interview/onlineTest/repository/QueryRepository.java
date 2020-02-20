package com.interview.onlineTest.repository;

import com.interview.onlineTest.dto.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@Repository
public class QueryRepository {

    @Autowired
    JdbcTemplate template;

    private static final String ANSWERS_QUERY = "select text from answers where query_id = ";
    public List<String> getAnswers(int query_id){
        return template.queryForList(ANSWERS_QUERY + query_id, String.class);
    }

    private static final String QUERIES_QUERY = "select id, text, type, right_answer from Queries";
    public List<Query> getAllQueries(boolean withRightAnswer){

        List<Query> queries = template.query(QUERIES_QUERY,(result,rowNum)->new Query(result.getInt("id"),
                result.getString("text"),
                result.getInt("type"),
                withRightAnswer ? result.getInt("right_answer") : 0,
                result.getInt("type") == 1 ? getAnswers(result.getInt("id")) : new ArrayList<String>()
        ));
        return queries;
    }

    private static final String ADD_ANSWER_QUERY = "INSERT INTO Answers (text, query_num, query_id) VALUES (?,?,?)";
    public void addAnswer(String text, int query_num, int query_id) {
       template.update(ADD_ANSWER_QUERY, text, query_num, query_id);
    }

    private static final String ADD_QUERY_QUERY = "INSERT INTO Queries (text, type, right_answer) VALUES (?,?,?)";
    public synchronized int addQuery(Query query) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resultAdd = template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(ADD_QUERY_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, query.getText());
                ps.setInt(2, query.getType());
                ps.setInt(3, query.getRight_answer());

                return ps;
            }
        }, keyHolder);
        if (resultAdd > 0) {
            int numberQuery = 0;
            for (String answer: query.getAnswers()) {
                addAnswer(answer, numberQuery, keyHolder.getKey().intValue());
                numberQuery++;
            }
        }
        return resultAdd;
    }
}

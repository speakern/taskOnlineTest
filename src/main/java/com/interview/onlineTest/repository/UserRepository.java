package com.interview.onlineTest.repository;

import com.interview.onlineTest.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate template;

    private static final String FIND_USER_QUERY = "SELECT id, username, password, enabled FROM USERS where username = ?";
    public User findByUsername(String userName) {

        try {
            User user = template.queryForObject(FIND_USER_QUERY, (result, rowNum) ->
                    new User(result.getInt("id"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getBoolean("enabled")), userName);
            return user;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static final String ADD_ROLE_QUERY = "INSERT INTO authorities(user_id, authority) VALUES(?, ?)";
    public int addRole(int userID, String role) {
        int resultAdd = template.update(ADD_ROLE_QUERY, userID, role);

        return resultAdd;
    }

    private static final String CREATE_USER_QUERY = "INSERT INTO users(username,password,enabled) VALUES (?,?,?)";
    public synchronized int addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int resultAdd = template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, new BCryptPasswordEncoder().encode(user.getPassword()));
                ps.setBoolean(3, user.isEnabled());

                return ps;
            }
        }, keyHolder);

        if (resultAdd > 0) {
            addRole(keyHolder.getKey().intValue(), "ROLE_USER");
        }
        return resultAdd;
    }

    private static final String LIST_USERS_QUERY ="SELECT id, username, enabled FROM USERS ";
    public List<User> getUsers(){

        List<User> users = template.query(LIST_USERS_QUERY,(result,rowNum)-> new User(
                result.getInt("id"),
                result.getString("username"),
                "",
                result.getBoolean("enabled")
        ));
        return users;
    }

}

package com.interview.onlineTest.controller;

import com.interview.onlineTest.dto.Response;
import com.interview.onlineTest.dto.User;
import com.interview.onlineTest.exceptions.InternalServerErrorExeption;
import com.interview.onlineTest.exceptions.UserExistsException;
import com.interview.onlineTest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    public ResponseEntity<Response> addUser(@RequestBody @Valid User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            logger.warn("Пользователь {} уже существует", user.getUsername());
            throw new UserExistsException(userFromDb.getUsername());
        }

        user.setEnabled(true);

        if (userRepository.addUser(user) >= 1){
            logger.info("Пользователь {}  успешно добавлен", user.getUsername());
            return ResponseEntity.ok().body(new Response( "User Added Successfully", ""));
        } else {
            logger.error("Ошибка добавления пользователя");
            throw new InternalServerErrorExeption();
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.getUsers();
    }

}

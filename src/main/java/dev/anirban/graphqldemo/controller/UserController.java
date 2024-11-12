package dev.anirban.graphqldemo.controller;


import dev.anirban.graphqldemo.entity.User;
import dev.anirban.graphqldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @MutationMapping
    public User createUser(
            @Argument String name,
            @Argument String email,
            @Argument String photoUrl
    ) {
        return service.createUser(name, email, photoUrl);
    }

    @QueryMapping
    public List<User> findAllUsers() {
        return service.findAllUsers();
    }

    @QueryMapping
    public User findUserById(@Argument String id) {
        return service.findUserById(id);
    }

    @MutationMapping
    public String deleteUser(@Argument String id) {
        service.deleteUser(id);
        return "User deleted Successfully";
    }
}

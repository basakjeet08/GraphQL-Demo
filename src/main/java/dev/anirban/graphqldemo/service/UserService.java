package dev.anirban.graphqldemo.service;


import dev.anirban.graphqldemo.entity.User;
import dev.anirban.graphqldemo.exception.UserNotFound;
import dev.anirban.graphqldemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findUserById(String id) {
        return userRepo
                .findById(id)
                .orElseThrow(() -> new UserNotFound(id));
    }

    public void deleteUser(String id) {
        if (!userRepo.existsById(id))
            throw new UserNotFound(id);

        userRepo.deleteById(id);
    }
}

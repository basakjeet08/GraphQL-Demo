package dev.anirban.graphqldemo.repository;

import dev.anirban.graphqldemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}

package dev.anirban.graphqldemo.repository;

import dev.anirban.graphqldemo.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FacultyRepository extends JpaRepository<Faculty, String> {
    List<Faculty> findByNameContaining(String name);
}

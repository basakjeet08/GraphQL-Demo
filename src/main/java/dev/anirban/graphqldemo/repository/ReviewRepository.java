package dev.anirban.graphqldemo.repository;

import dev.anirban.graphqldemo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByCreatedBy_Uid(String userId);

    List<Review> findByCreatedFor_Id(String facultyId);
}
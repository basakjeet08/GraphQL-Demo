package dev.anirban.graphqldemo.controller;

import dev.anirban.graphqldemo.entity.Review;
import dev.anirban.graphqldemo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @QueryMapping
    public Review createReview(
            @Argument Double rating,
            @Argument String feedback,
            @Argument String createdBy,
            @Argument String createdFor
    ) {
        return service.createReview(rating, feedback, createdBy, createdFor);
    }

    @QueryMapping
    public List<Review> findAllReviews() {
        return service.findAllReviews();
    }

    @QueryMapping
    public Review findReviewById(@Argument String id) {
        return service.findReviewById(id);
    }

    @QueryMapping
    public List<Review> findReviewByUserId(@Argument String id) {
        return service.findReviewByUserId(id);
    }

    @QueryMapping
    public List<Review> findReviewByFacultyId(@Argument String id) {
        return service.findReviewByFacultyId(id);
    }

    @QueryMapping
    public String deleteReview(@Argument String id) {
        service.deleteReview(id);
        return "Review Successfully Deleted";
    }
}

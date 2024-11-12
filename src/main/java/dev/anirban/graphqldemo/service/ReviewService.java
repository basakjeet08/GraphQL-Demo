package dev.anirban.graphqldemo.service;

import dev.anirban.graphqldemo.dto.PostReviewRequest;
import dev.anirban.graphqldemo.entity.Faculty;
import dev.anirban.graphqldemo.entity.Review;
import dev.anirban.graphqldemo.entity.User;
import dev.anirban.graphqldemo.enums.Validation;
import dev.anirban.graphqldemo.exception.*;
import dev.anirban.graphqldemo.repository.FacultyRepository;
import dev.anirban.graphqldemo.repository.ReviewRepository;
import dev.anirban.graphqldemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepo;
    private final FacultyRepository facultyRepo;
    private final ReviewRepository reviewRepo;
    private final ProfanityService profanityService;

    public Review createReview(PostReviewRequest review) {

        // Fetching user who created the Review
        User user = userRepo
                .findById(review.getCreatedBy())
                .orElseThrow(() -> new UserNotFound(review.getCreatedBy()));

        // Fetching Faculty for which the review is created
        Faculty faculty = facultyRepo
                .findById(review.getCreatedFor())
                .orElseThrow(() -> new FacultyNotFound(review.getCreatedFor()));

        // Check if the user has already reviewed this faculty
        if (checkIfAlreadyReviewed(user, faculty))
            throw new ReviewAlreadyExistsException();


        // Checking if the given review contains bad words or not.
        if (profanityService.containsProfanity(review.getFeedback()))
            throw new ProfanityFoundException();


        // Creating the Date Format
        String formattedDate = createFormatterDate();


        // Building the Review Object
        Review newReview = Review
                .builder()
                .rating(review.getRating())
                .feedback(review.getFeedback())
                .status(Validation.NOT_VALIDATED)
                .createdAt(formattedDate)
                .createdBy(user)
                .createdFor(faculty)
                .build();

        user.addReview(newReview);
        faculty.addReview(newReview);

        // Storing the new Review created
        return reviewRepo.save(newReview);
    }

    private String createFormatterDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    private boolean checkIfAlreadyReviewed(User user, Faculty faculty) {
        return user
                .getReviewsGiven()
                .stream()
                .anyMatch(existingReview ->
                        existingReview
                                .getCreatedFor()
                                .getId()
                                .equals(faculty.getId())
                );
    }

    public List<Review> findAllReviews() {
        return reviewRepo.findAll();
    }

    public Review findReviewById(String id) {
        return reviewRepo
                .findById(id)
                .orElseThrow(() -> new ReviewNotFound(id));
    }

    public List<Review> findReviewByUserId(String userId) {
        return reviewRepo.findByCreatedBy_Uid(userId);
    }

    public List<Review> findReviewByFacultyId(String facultyId) {
        return reviewRepo.findByCreatedFor_Id(facultyId);
    }

    public void deleteReview(String id) {

        // Fetching the review to be deleted
        Review review = reviewRepo
                .findById(id)
                .orElseThrow(() -> new ReviewNotFound(id));

        // Fetching the faculty for which the review is given
        Faculty faculty = facultyRepo
                .findById(review.getCreatedFor().getId())
                .orElseThrow(() -> new FacultyNotFound(review.getCreatedFor().getId()));

        // Fetching the user who gave the review
        User user = userRepo
                .findById(review.getCreatedBy().getUid())
                .orElseThrow(() -> new UserNotFound(review.getCreatedBy().getUid()));

        faculty.removeReview(review);
        user.removeReview(review);

        // Deleting the old Review
        reviewRepo.deleteById(id);
    }
}

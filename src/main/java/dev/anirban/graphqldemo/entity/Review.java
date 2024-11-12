package dev.anirban.graphqldemo.entity;


import dev.anirban.graphqldemo.enums.Validation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Remove;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REVIEW_DB")
public class Review {

    @Id
    @UuidGenerator
    private String id;
    private Double rating;
    private String feedback;
    @Enumerated(value = EnumType.STRING)
    private Validation status;
    private String createdAt;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    private User createdBy;

    @ManyToOne(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER
    )
    private Faculty createdFor;

    @Remove
    public void beforeReviewRemove() {
        createdFor.removeReview(this);
    }
}
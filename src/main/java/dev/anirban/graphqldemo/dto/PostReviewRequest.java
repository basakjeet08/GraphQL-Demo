package dev.anirban.graphqldemo.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostReviewRequest {
    private String createdBy;
    private String createdFor;
    private Double rating;
    private String feedback;
}
package trip.milliage.entity.review;

import lombok.*;
import trip.milliage.common.ActionCase;
import trip.milliage.entity.attachedPhoto.AttachedPhoto;
import trip.milliage.entity.review.Review;

import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Add{
        @NotNull
        private UUID reviewId;

        @NotNull
        private String content;

        @NotNull
        private List<UUID> attachedPhotoIds;

        @NotNull
        private UUID userId;

        @NotNull
        private UUID placeId;

        @NotNull
        private ActionCase actionCase;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update{
        @NotNull
        private UUID reviewId;

        @NotNull
        private String content;

        @NotNull
        private UUID userId;

        @NotNull
        private List<UUID> attachedPhotoIds;

        @NotNull
        private ActionCase actionCase;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Delete{
        @NotNull
        private UUID reviewId;

        @NotNull
        private UUID userId;

        @NotNull
        private ActionCase actionCase;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        @NotNull
        private UUID reviewId;

        @NotNull
        private String content;

        @NotNull
        private List<UUID> attachedPhotoIds;

        @NotNull
        private UUID userId;

        @NotNull
        private UUID placeId;
    }

    public static Response of(Review review){
        List<UUID> list = review.getAttachedPhotoList().stream()
                .map(AttachedPhoto::getPhotoId)
                .collect(Collectors.toList());

        return Response.builder()
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(list)
                .userId(review.getUserId())
                .placeId(review.getPlaceId())
                .build();
    }
}

package trip.milliage.entity.event;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trip.milliage.entity.review.ReviewService;
import trip.milliage.entity.review.ReviewDto;
import trip.milliage.exception.BusinessLogicException;
import trip.milliage.exception.ExceptionCode;

@RestController
@RequestMapping("/events")
public class EventController {
    private final ReviewService reviewService;

    public EventController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity eventMatching(@Validated @RequestBody EventDto.ReqeustDto dto){

        switch(dto.getAction()){
            case ADD:
                ReviewDto.Add addReview = ReviewDto.Add.builder()
                        .reviewId(dto.getReviewId())
                        .content(dto.getContent())
                        .attachedPhotoIds(dto.getAttachedPhotoIds())
                        .userId(dto.getUserId())
                        .placeId(dto.getPlaceId())
                        .actionCase(dto.getAction())
                        .build();
                return reviewService.addReview(addReview);
            case MOD:
                ReviewDto.Update update = ReviewDto.Update.builder()
                        .reviewId(dto.getReviewId())
                        .content(dto.getContent())
                        .userId(dto.getUserId())
                        .attachedPhotoIds(dto.getAttachedPhotoIds())
                        .actionCase(dto.getAction())
                        .build();
                return reviewService.updateReview(update);
            case DELETE:
                ReviewDto.Delete delete = ReviewDto.Delete.builder()
                        .reviewId(dto.getReviewId())
                        .userId(dto.getUserId())
                        .actionCase(dto.getAction())
                        .build();
                return reviewService.deleteReview(delete);
            default:
                throw new BusinessLogicException(ExceptionCode.TYPE_NOT_FOUNT);
        }
    }
}

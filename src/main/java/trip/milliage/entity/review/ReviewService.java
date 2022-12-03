package trip.milliage.entity.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip.milliage.common.ActionCase;
import trip.milliage.entity.attachedPhoto.AttachedPhoto;
import trip.milliage.entity.attachedPhoto.AttachedPhotoService;
import trip.milliage.entity.point.Point;
import trip.milliage.entity.point.PointService;
import trip.milliage.entity.member.Member;
import trip.milliage.entity.member.MemberService;
import trip.milliage.exception.BusinessLogicException;
import trip.milliage.exception.ExceptionCode;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberService userService;
    private final PointService pointService;
    private final AttachedPhotoService attachedPhotoService;

    public ReviewService(ReviewRepository reviewRepository, MemberService userService, PointService pointService, AttachedPhotoService attachedPhotoService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.pointService = pointService;
        this.attachedPhotoService = attachedPhotoService;
    }

    public ResponseEntity addReview(ReviewDto.Add dto){
        isWriteSamePlace(dto);

        Review review = Review.builder()
                .id(dto.getReviewId())
                .userId(dto.getUserId())
                .content(dto.getContent())
                .placeId(dto.getPlaceId())
                .build();

        for(UUID id : dto.getAttachedPhotoIds()){
            AttachedPhoto attachedPhoto = attachedPhotoService.findAttachedPhoto(review, id);
            review.addPhoto(attachedPhoto);
        }

        long point = calPoint(review, dto.getActionCase());
        updateEntity(review, dto.getActionCase(), point);

        review.setBonus(true);
        reviewRepository.save(review);

        return new ResponseEntity(ReviewDto.of(review),HttpStatus.OK);
    }

    public ResponseEntity updateReview(ReviewDto.Update dto){
        Review review = isExistReview(dto.getReviewId());

        long point = calPointUpdate(review, dto);

        review.getAttachedPhotoList().stream()
                .filter(attachedPhoto -> !dto.getAttachedPhotoIds().contains(attachedPhoto.getPhotoId()))
                .collect(Collectors.toList())
                .forEach(attachedPhoto -> review.deletePhoto(attachedPhoto));

        for(UUID id : dto.getAttachedPhotoIds()){
            AttachedPhoto attachedPhoto = attachedPhotoService.findAttachedPhoto(review, id);
            review.addPhoto(attachedPhoto);
        }

        updateEntity(review, dto.getActionCase(), point);

        review.setContent(dto.getContent());
        reviewRepository.save(review);

        return new ResponseEntity(ReviewDto.of(review), HttpStatus.OK);
    }

    public ResponseEntity deleteReview(ReviewDto.Delete dto){
        Review findReview = isExistReview(dto.getReviewId());

        long point = calPoint(findReview, dto.getActionCase());
        updateEntity(findReview, dto.getActionCase(), point);

        reviewRepository.delete(findReview);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public void isWriteSamePlace(ReviewDto.Add dto){
        if(reviewRepository.findByUserIdAndPlaceId(dto.getUserId(), dto.getPlaceId()).isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_REVIEW_EXIST);
        }
    }

    public boolean isFirstReview(Review review){
        if(reviewRepository.existByPlaceId(review.getPlaceId()).isEmpty()){
            return true;
        }
        return false;
    }

    public Review isExistReview(UUID id){
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if(optionalReview.isEmpty())
            throw new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUNT);
        return optionalReview.get();
    }

    public long calPointUpdate(Review review, ReviewDto.Update dto){
        long prevPoint = 0L;
        if(review.getContent().trim().length() != 0){
            prevPoint++;
        }
        if(review.getAttachedPhotoList().size() != 0){
            prevPoint++;
        }

        long nowPoint = 0L;
        if(dto.getContent().trim().length() != 0){
            nowPoint++;
        }
        if(dto.getAttachedPhotoIds().size() != 0){
            nowPoint++;
        }

        long updatePoint = nowPoint - prevPoint;

        return updatePoint;
    }

    public long calPoint(Review review, ActionCase actionCase){
        long amount = 0L;

        if(review.getContent().trim().length() != 0){
            amount++;
        }

        if(review.getAttachedPhotoList().size() != 0){
            amount++;
        }

        if(actionCase == ActionCase.ADD && isFirstReview(review)){
            review.setBonus(true);
            amount++;
        }

        if(actionCase == ActionCase.DELETE && review.getBonus())
            amount++;

        return amount;
    }

    public void updateEntity(Review review, ActionCase actionCase, long point){
        Member member = userService.findUserOrCreate(review.getUserId());
        pointService.savePoint(new Point(UUID.randomUUID(), review.getUserId(), review.getReviewId(), point, actionCase));
        if(actionCase == ActionCase.DELETE)
            member.setCount(member.getCount() - point);
        else
            member.setCount(member.getCount() + point);
        userService.updateUser(member);
    }
}

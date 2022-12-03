package trip.milliage.reviewTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import trip.milliage.common.ActionCase;
import trip.milliage.entity.attachedPhoto.AttachedPhoto;
import trip.milliage.entity.attachedPhoto.AttachedPhotoService;
import trip.milliage.entity.member.Member;
import trip.milliage.entity.member.MemberService;
import trip.milliage.entity.point.Point;
import trip.milliage.entity.point.PointService;
import trip.milliage.entity.review.Review;
import trip.milliage.entity.review.ReviewDto;
import trip.milliage.entity.review.ReviewRepository;
import trip.milliage.entity.review.ReviewService;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Review Service Test")
public class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private PointService pointService;

    @Mock
    private AttachedPhotoService attachedPhotoService;

    @Test
    @DisplayName("플레이스 첫 리뷰 추가시 포인트 3 추가")
    void calPointAdd(){
        long result = 3L;
        Review review = new Review(UUID.randomUUID(), UUID.randomUUID(), "test",UUID.randomUUID());
        Set<AttachedPhoto> attachedPhotos = new HashSet<>(Set.of(
                new AttachedPhoto(1L,UUID.randomUUID(), review),
                new AttachedPhoto(2L, UUID.randomUUID(), review)
        ));
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        assertThat(reviewService.calPoint(review, ActionCase.ADD)).isEqualTo(result);
    }

    @Test
    @DisplayName("플레이스 첫 리뷰 추가시 포인트 2 추가_노포토")
    void calPointAdd_NoPhoto(){
        long result = 2L;
        Review review = new Review(UUID.randomUUID(), UUID.randomUUID(), "test",UUID.randomUUID());
        Set<AttachedPhoto> attachedPhotos = new HashSet<>();
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        assertThat(reviewService.calPoint(review, ActionCase.ADD)).isEqualTo(result);
    }

    @Test
    @DisplayName("이미 리뷰가 있는 플레이스 리뷰추가 2포인트_ 두번쨰리뷰")
    void calPointAdd_NoFirstPlace(){
        long result = 2L;
        UUID placeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        Review review = new Review(reviewId, UUID.randomUUID(), "test",placeId);
        Set<AttachedPhoto> attachedPhotos = new HashSet<>(Set.of(
                new AttachedPhoto(1L,UUID.randomUUID(), review),
                new AttachedPhoto(2L, UUID.randomUUID(), review)
        ));
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        given(reviewRepository.existByPlaceId(placeId)).willReturn(Optional.of(new Review()));
        assertThat(reviewService.calPoint(review, ActionCase.ADD)).isEqualTo(result);
    }

    @Test
    @DisplayName("기존 2점 -> 사진없는제거하여 -1점")
    void calPointUpDate(){
        long result = -1L;
        UUID placeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Review review = new Review(reviewId, userId, "test",placeId);
        Set<AttachedPhoto> attachedPhotos = new HashSet<>(Set.of(
                new AttachedPhoto(1L,UUID.randomUUID(), review),
                new AttachedPhoto(2L, UUID.randomUUID(), review)
        ));
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        ReviewDto.Update update = new ReviewDto.Update(reviewId, "test2", userId, new ArrayList<>(), ActionCase.MOD);

        assertThat(reviewService.calPointUpdate(review, update)).isEqualTo(result);
    }

    @Test
    @DisplayName("장소 첫번째 리뷰 지웟을때 지울 점수 계산")
    void calPointDelete(){
        long result = 3L;
        UUID placeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Review review = new Review(reviewId, userId, "test",placeId);
        review.setBonus(true);
        Set<AttachedPhoto> attachedPhotos = new HashSet<>(Set.of(
                new AttachedPhoto(1L,UUID.randomUUID(), review),
                new AttachedPhoto(2L, UUID.randomUUID(), review)
        ));
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        assertThat(reviewService.calPoint(review, ActionCase.DELETE)).isEqualTo(result);
    }

    @Test
    @DisplayName("장소 두번째 리뷰 지웟을때 지울 점수 계산")
    void calPointDelete_Second(){
        long result = 2L;
        UUID placeId = UUID.randomUUID();
        UUID reviewId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        Review review = new Review(reviewId, userId, "test",placeId);
        Set<AttachedPhoto> attachedPhotos = new HashSet<>(Set.of(
                new AttachedPhoto(1L,UUID.randomUUID(), review),
                new AttachedPhoto(2L, UUID.randomUUID(), review)
        ));
        attachedPhotos.stream().forEach(x -> review.addPhoto(x));

        assertThat(reviewService.calPoint(review, ActionCase.DELETE)).isEqualTo(result);
    }
}

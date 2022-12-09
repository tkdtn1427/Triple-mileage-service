package trip.milliage.entity.review;

import static trip.milliage.entity.review.QReview.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ReviewQueryRepositoryImpl implements ReviewQueryRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Review> existByPlaceId(UUID placeId) {
        return Optional.ofNullable(jpaQueryFactory.select(review)
                .from(review)
                .where(review.placeId.eq(placeId))
                .fetchOne());
    }
}

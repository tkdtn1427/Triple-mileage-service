package trip.milliage.entity.review;

import java.util.Optional;
import java.util.UUID;

public interface ReviewQueryRepository {
    Optional<Review> existByPlaceId(UUID placeId);
}

package trip.milliage.entity.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    @Query(value = "SELECT * FROM REVIEW WHERE USER_ID = :userId AND PLACE_ID = :placeId", nativeQuery = true)
    Optional<Review> findByUserIdAndPlaceId(UUID userId, UUID placeId);

    @Query(value = "SELECT * FROM REVIEW WHERE PLACE_ID = :placeId", nativeQuery = true)
    Optional<Review> existByPlaceId(UUID placeId);
}

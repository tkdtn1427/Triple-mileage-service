package trip.milliage.entity.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewQueryRepository{
    @Query(value = "SELECT * FROM REVIEW WHERE USER_ID = :userId AND PLACE_ID = :placeId", nativeQuery = true)
    Optional<Review> findByUserIdAndPlaceId(UUID userId, UUID placeId);

//    @Query(value = "SELECT * FROM REVIEW WHERE PLACE_ID = :placeId", nativeQuery = true)
//    Optional<Review> existByPlaceId(UUID placeId);
}

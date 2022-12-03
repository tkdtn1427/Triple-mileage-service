package trip.milliage.entity.attachedPhoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AttachedPhtoRepository extends JpaRepository<AttachedPhoto, UUID> {
    @Query(value = "SELECT * FROM ATTACHED_PHOTO WHERE REVIEW_ID = :reviewId AND PHOTO_ID = :photoId", nativeQuery = true)
    Optional<AttachedPhoto> existByReviewIddAndPhoto(UUID reviewId, UUID photoId);
}

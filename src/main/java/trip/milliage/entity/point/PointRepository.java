package trip.milliage.entity.point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PointRepository extends JpaRepository<Point, UUID> {
    @Query(value = "SELECT * FROM POINT WHERE USER_ID = :userId", nativeQuery = true)
    Page<Point> findByUserId(Pageable pageable, UUID userId);
}

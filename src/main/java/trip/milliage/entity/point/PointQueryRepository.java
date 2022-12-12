package trip.milliage.entity.point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PointQueryRepository {
    Page<Point> findByUserId(Pageable pageable, UUID userId);
}

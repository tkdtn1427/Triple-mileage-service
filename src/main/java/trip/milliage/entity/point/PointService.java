package trip.milliage.entity.point;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import trip.milliage.common.MultiResponseDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PointService {
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public Point savePoint(Point point){
        return pointRepository.save(point);
    }

    public ResponseEntity getPointHistory(Pageable pageable, UUID userId){
        Page<Point> pointPage = pointRepository.findByUserId(pageable, userId);
        List<PointHistory> pointHistories = pointPage.stream().map(PointHistory::of).collect(Collectors.toList());
        return new ResponseEntity(new MultiResponseDto<>(pointHistories, pointPage), HttpStatus.OK);
    }

    public ResponseEntity getAllPointHistory(Pageable pageable){
        Page<Point> pointPage = pointRepository.findAll(pageable);
        List<PointHistory> pointHistories = pointPage.stream().map(PointHistory::of).collect(Collectors.toList());
        return new ResponseEntity(new MultiResponseDto<>(pointHistories, pointPage), HttpStatus.OK);
    }
}

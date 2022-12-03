package trip.milliage.entity.point;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/history")
public class PointController {
    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity getUserPointHistory(@PageableDefault(sort = "CREATED_AT", direction = Sort.Direction.DESC) Pageable pageable,
                                              @RequestParam UUID userId){
        return pointService.getPointHistory(pageable,userId);
    }

    @GetMapping("/all")
    public ResponseEntity getAllPointHistory(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        return pointService.getAllPointHistory(pageable);
    }
}

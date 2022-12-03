package trip.milliage.entity.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import trip.milliage.common.ActionCase;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PointHistory {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID reviewId;

    @NotNull
    private ActionCase actionCase;

    @NotNull
    private long point;

    private LocalDateTime localDateTime;

    public static PointHistory of(Point point){
        return PointHistory.builder()
                .userId(point.getUserId())
                .reviewId(point.getReviewId())
                .actionCase(point.getActionCase())
                .point(point.getAmount())
                .localDateTime(point.getCreatedAt())
                .build();
    }
}

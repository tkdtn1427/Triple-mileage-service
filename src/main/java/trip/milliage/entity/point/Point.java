package trip.milliage.entity.point;

import lombok.*;
import trip.milliage.common.ActionCase;
import trip.milliage.common.Auditable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Point extends Auditable {
    @Id
    @Column(columnDefinition = "Binary(16)")
    private UUID pointId;

    @Column(columnDefinition = "Binary(16)")
    private UUID userId;

    @Column(columnDefinition = "Binary(16)")
    private UUID reviewId;

    @Column
    private long amount;

    @Column
    private ActionCase actionCase;
}

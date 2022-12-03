package trip.milliage.entity.member;

import lombok.*;
import trip.milliage.common.Auditable;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends Auditable {
    @Id
    @Column(columnDefinition = "Binary(16)")
    private UUID memberId;

    @Column
    private long count;
}

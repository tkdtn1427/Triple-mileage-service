package trip.milliage.entity.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberPointResponse {
    @NotNull
    private UUID userId;

    @NotNull
    private long count;

    public static MemberPointResponse of(Member member){
        return MemberPointResponse.builder()
                .userId(member.getMemberId())
                .count(member.getCount())
                .build();
    }
}

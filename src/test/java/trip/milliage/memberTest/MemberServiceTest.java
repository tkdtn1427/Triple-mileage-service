package trip.milliage.memberTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Nested;
import org.mockito.junit.jupiter.MockitoExtension;
import trip.milliage.entity.member.Member;
import trip.milliage.entity.member.MemberRepository;
import trip.milliage.entity.member.MemberService;
import trip.milliage.exception.BusinessLogicException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Service Test")
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("유저 찾기 - 성공,실패")
    class MemberExist{
        private final UUID userId = UUID.randomUUID();

        @DisplayName("유저가 존재하지 않으면 예외 발생")
        @Test
        public void existUserByIdTest_Thrown(){
            //given
            Optional<Member> member = Optional.empty();

            //mocking
            given(memberRepository.findById(Mockito.any(UUID.class))).willReturn(member);

            assertThatThrownBy(()->memberService.existUserById(userId)).isInstanceOf(BusinessLogicException.class);
        }

    }


}

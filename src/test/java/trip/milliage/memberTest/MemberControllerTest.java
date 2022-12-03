package trip.milliage.memberTest;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import trip.milliage.entity.member.Member;
import trip.milliage.entity.member.MemberController;
import trip.milliage.entity.member.MemberPointResponse;
import trip.milliage.entity.member.MemberService;

import java.util.UUID;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@DisplayName("유저 포인트 조회 컨트롤러 테스트")
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("유저의 현재 포인트 조회")
    void findMemberPointTest() throws Exception {
        final UUID userId = UUID.randomUUID();

        Member member = new Member(userId,3);
        MemberPointResponse memberPointResponse = MemberPointResponse.of(member);
        ResponseEntity response = new ResponseEntity(memberPointResponse, HttpStatus.OK);

        given(memberService.findUserPoint(Mockito.any(UUID.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(get("/point")
                        .queryParam("user-id",userId.toString())
                .accept(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(memberPointResponse.getUserId().toString()))
                .andExpect(jsonPath("$.count").value(memberPointResponse.getCount())).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}

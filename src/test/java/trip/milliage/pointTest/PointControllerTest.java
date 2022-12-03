package trip.milliage.pointTest;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import trip.milliage.common.ActionCase;
import trip.milliage.common.MultiResponseDto;
import trip.milliage.entity.point.Point;
import trip.milliage.entity.point.PointController;
import trip.milliage.entity.point.PointHistory;
import trip.milliage.entity.point.PointService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebMvcTest(PointController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@DisplayName("유저 포인트 이력 조회 테스트")
public class PointControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private PointService pointService;

    @Test
    @DisplayName("특정 유저 포인트 이력 조회 테스트")
    void getUserPointHistoryTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0,3, Sort.by("createdAt").descending());
        UUID userId1 = UUID.randomUUID();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("page","1");
        map.add("size","3");

        Point point1 = new Point(UUID.randomUUID(), userId1, UUID.randomUUID(), 4, ActionCase.ADD);
        Point point2 = new Point(UUID.randomUUID(), userId1, UUID.randomUUID(), 1, ActionCase.MOD);

        Page<Point> page = new PageImpl<>(List.of(point1,point2),pageRequest, 2);
        List<PointHistory> pointHistories = page.stream().map(PointHistory::of).collect(Collectors.toList());
        ResponseEntity response = new ResponseEntity(new MultiResponseDto<>(pointHistories, page), HttpStatus.OK);

        given(pointService.getPointHistory(Mockito.any(Pageable.class),Mockito.any(UUID.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(get("/history")
                .params(map)
                .queryParam("userId",userId1.toString())
                .accept(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(point1.getUserId().toString()))
                .andExpect(jsonPath("$.data[1].userId").value(point2.getUserId().toString()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("모든 유저 포인트 이력 조회 성공 테스트")
    void getAllPointHistoryTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0,3, Sort.by("createdAt").descending());
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("page","1");
        map.add("size","3");

        Point point1 = new Point(UUID.randomUUID(), userId1, UUID.randomUUID(), 4, ActionCase.ADD);
        Point point2 = new Point(UUID.randomUUID(), userId2, UUID.randomUUID(), 1, ActionCase.MOD);

        Page<Point> page = new PageImpl<>(List.of(point1,point2),pageRequest, 2);
        List<PointHistory> pointHistories = page.stream().map(PointHistory::of).collect(Collectors.toList());
        ResponseEntity response = new ResponseEntity(new MultiResponseDto<>(pointHistories, page), HttpStatus.OK);

        given(pointService.getAllPointHistory(Mockito.any(Pageable.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(get("/history" + "/all")
                .params(map)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(point1.getUserId().toString()))
                .andExpect(jsonPath("$.data[1].userId").value(point2.getUserId().toString()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("모든 유저 포인트 이력 조회 실패 테스트")
    void getAllPointHistoryTest_Fail() throws Exception {
        PageRequest pageRequest = PageRequest.of(0,3, Sort.by("createdAt").descending());
        UUID userId1 = UUID.randomUUID();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("page","1");
        map.add("size","3");

        Point point1 = new Point(UUID.randomUUID(), userId1, UUID.randomUUID(), 4, ActionCase.ADD);
        Point point2 = new Point(UUID.randomUUID(), userId1, UUID.randomUUID(), 1, ActionCase.MOD);

        Page<Point> page = new PageImpl<>(List.of(point1,point2),pageRequest, 2);
        List<PointHistory> pointHistories = page.stream().map(PointHistory::of).collect(Collectors.toList());
        ResponseEntity response = new ResponseEntity(new MultiResponseDto<>(pointHistories, page), HttpStatus.OK);

        given(pointService.getAllPointHistory(Mockito.any(Pageable.class))).willReturn(response);

        ResultActions actions = mockMvc.perform(get("/history" + "/all")
                .params(map)
                .accept(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(point1.getUserId().toString()))
                .andExpect(jsonPath("$.data[1].userId").value(point2.getUserId().toString()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}

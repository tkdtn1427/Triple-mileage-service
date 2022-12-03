package trip.milliage.eventTest;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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
import trip.milliage.common.ActionCase;
import trip.milliage.entity.event.EventController;
import trip.milliage.entity.event.EventDto;
import trip.milliage.entity.review.Review;
import trip.milliage.entity.review.ReviewDto;
import trip.milliage.entity.review.ReviewService;

import java.util.ArrayList;
import java.util.UUID;

@WebMvcTest(EventController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)
@DisplayName("이벤트 컨트롤러")
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private ReviewService reviewService;

    @Nested
    @DisplayName("이벤트 타입에 따른 서비스")
    class EventTest{
        @Test
        @DisplayName("Type : 리뷰 ADD 이벤트")
        void addReviewEvent() throws Exception {
            EventDto.ReqeustDto reqeustDto = new EventDto.ReqeustDto("Review", ActionCase.ADD, UUID.randomUUID(), "contents is ready", new ArrayList<>(), UUID.randomUUID(), UUID.randomUUID());
            String content = gson.toJson(reqeustDto);

            Review review = new Review(reqeustDto.getReviewId(), reqeustDto.getUserId(), reqeustDto.getContent(), reqeustDto.getPlaceId());
            ResponseEntity response = new ResponseEntity(ReviewDto.of(review), HttpStatus.OK);

            given(reviewService.addReview(Mockito.any(ReviewDto.Add.class))).willReturn(response);

            ResultActions actions = mockMvc.perform(post("/events")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content));

            MvcResult result = actions.andExpect(status().isOk())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Type : 리뷰 Delete 이벤트")
        void DeleteReviewEvent() throws Exception {
            EventDto.ReqeustDto reqeustDto = new EventDto.ReqeustDto("Review", ActionCase.DELETE, UUID.randomUUID(), "contents is ready", new ArrayList<>(), UUID.randomUUID(), UUID.randomUUID());
            String content = gson.toJson(reqeustDto);

            Review review = new Review(reqeustDto.getReviewId(), reqeustDto.getUserId(), reqeustDto.getContent(), reqeustDto.getPlaceId());
            ResponseEntity response = new ResponseEntity(ReviewDto.of(review), HttpStatus.OK);

            given(reviewService.updateReview(Mockito.any(ReviewDto.Update.class))).willReturn(response);

            ResultActions actions = mockMvc.perform(post("/events")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content));

            MvcResult result = actions.andExpect(status().isOk())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        @Test
        @DisplayName("Type : MOD 이벤트 ")
        void ExceptionReviewEvent() throws Exception {
            EventDto.ReqeustDto reqeustDto = new EventDto.ReqeustDto("Review", ActionCase.DELETE, UUID.randomUUID(), "contents is ready", new ArrayList<>(), UUID.randomUUID(), UUID.randomUUID());
            String content = gson.toJson(reqeustDto);

            ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);

            given(reviewService.deleteReview(Mockito.any(ReviewDto.Delete.class))).willReturn(response);

            ResultActions actions = mockMvc.perform(post("/events")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content));

            MvcResult result = actions.andExpect(status().isNoContent())
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }
    }
}

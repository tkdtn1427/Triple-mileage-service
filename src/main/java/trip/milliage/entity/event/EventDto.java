package trip.milliage.entity.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import trip.milliage.common.ActionCase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class EventDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReqeustDto{
        @NotNull
        private String type;

        @NotNull
        private ActionCase action;

        @NotNull
        private UUID reviewId;

        private String content;

        private List<UUID> attachedPhotoIds;

        @NotNull
        private UUID userId;

        @NotNull
        private UUID placeId;
    }
}

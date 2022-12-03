package trip.milliage.exception;

import lombok.Getter;

public enum ExceptionCode {
    USER_NOT_FOUNT(404, "User not found"),
    ALREADY_REVIEW_EXIST(409, "Review is already exist in place"),
    REVIEW_NOT_FOUNT(404, "Review not found"),
    TYPE_NOT_FOUNT(404, "Type is not found");
    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}

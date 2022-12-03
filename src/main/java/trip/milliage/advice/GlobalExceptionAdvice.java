package trip.milliage.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import trip.milliage.exception.BusinessLogicException;
import trip.milliage.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }
}
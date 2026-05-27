package com.example.bfhl.exception;

import com.example.bfhl.dto.BfhlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String USER_ID     = "shikhi_parmar_26062004";
    private static final String EMAIL       = "shikhiparmar230715@acropolis.in";
    private static final String ROLL_NUMBER = "0827CS231243";

    /**
     * Builds a standard error payload with is_success = false and empty data fields.
     * Uses the builder field 'success' which is serialized as "is_success" via @JsonProperty.
     */
    private BfhlResponse buildErrorResponse() {
        return BfhlResponse.builder()
                .success(false)
                .userId(USER_ID)
                .email(EMAIL)
                .rollNumber(ROLL_NUMBER)
                .oddNumbers(Collections.emptyList())
                .evenNumbers(Collections.emptyList())
                .alphabets(Collections.emptyList())
                .specialCharacters(Collections.emptyList())
                .sum("0")
                .concatString("")
                .build();
    }

    /** Malformed JSON → 400 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BfhlResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse());
    }

    /** Validation failure → 400 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BfhlResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse());
    }

    /** Any other unexpected error → 500 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BfhlResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse());
    }
}

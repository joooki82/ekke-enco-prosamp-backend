package hu.jakab.ekkeencoprosampbackend.exception;

import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private String code;
    private Instant timestamp;
    private Map<String, String> details;

}

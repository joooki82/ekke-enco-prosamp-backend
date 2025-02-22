package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

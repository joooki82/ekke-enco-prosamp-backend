package hu.jakab.ekkeencoprosampbackend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String country;
    private String city;
    private String postalCode;
    private String taxNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

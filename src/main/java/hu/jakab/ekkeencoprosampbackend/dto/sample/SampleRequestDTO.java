package hu.jakab.ekkeencoprosampbackend.dto.sample;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleRequestDTO {

    @NotNull(message = "A mintavételi jegyzőkönyv azonosító megadása kötelező.")
    private Long samplingRecordId;

    @NotBlank(message = "A minta azonosító nem lehet üres.")
    private String sampleIdentifier;

    private String location;
    private String employeeName;

    @Digits(integer = 3, fraction = 2, message = "A hőmérséklet formátuma nem megfelelő (max. 3 egész, 2 tizedes).")
    private BigDecimal temperature;

    @Digits(integer = 3, fraction = 2, message = "A páratartalom formátuma nem megfelelő (max. 3 egész, 2 tizedes).")
    private BigDecimal humidity;

    @Digits(integer = 5, fraction = 2, message = "A nyomás formátuma nem megfelelő (max. 5 egész, 2 tizedes).")
    private BigDecimal pressure;

    @Digits(integer = 3, fraction = 4, message = "A térfogatáram formátuma nem megfelelő (max. 3 egész, 4 tizedes).")
    private BigDecimal sampleVolumeFlowRate;

    @NotNull(message = "A térfogatáram mértékegység azonosító megadása kötelező.")
    private Long sampleVolumeFlowRateUnitId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @NotBlank(message = "A minta típusa nem lehet üres.")
    private String sampleType;

    @NotBlank(message = "A státusz nem lehet üres.")
    private String status;

    @Size(max = 255, message = "A megjegyzés legfeljebb 255 karakter lehet.")
    private String remarks;

    private Long samplingTypeId;
    private Long adjustmentMethodId;
}

package hu.jakab.ekkeencoprosampbackend.dto.sampleAnalyticalResult;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleAnalyticalResultRequestDTO {

    @NotNull(message = "A minta-szennyező kapcsolat azonosítója kötelező.")
    private Long sampleContaminantId;

    @NotNull(message = "Az eredmény (fő érték) megadása kötelező.")
    @DecimalMin(value = "0.0", inclusive = true, message = "A fő érték nem lehet negatív.")
    @Digits(integer = 6, fraction = 4, message = "A fő érték formátuma nem megfelelő (max. 6 számjegy, 4 tizedes).")
    private BigDecimal resultMain;

    @DecimalMin(value = "0.0", inclusive = true, message = "A kontroll érték nem lehet negatív.")
    @Digits(integer = 6, fraction = 4, message = "A kontroll érték formátuma nem megfelelő (max. 6 számjegy, 4 tizedes).")
    private BigDecimal resultControl;

    @DecimalMin(value = "0.0", inclusive = true, message = "A fő+kontroll érték nem lehet negatív.")
    @Digits(integer = 6, fraction = 4, message = "A fő+kontroll érték formátuma nem megfelelő (max. 6 számjegy, 4 tizedes).")
    private BigDecimal resultMainControl;

    @NotNull(message = "A mértékegység azonosító megadása kötelező.")
    private Long resultMeasurementUnitId;

    private Boolean isBelowDetectionLimit;

    @DecimalMin(value = "0.0", inclusive = true, message = "A kimutatási határ nem lehet negatív.")
    @Digits(integer = 6, fraction = 4, message = "A kimutatási határ formátuma nem megfelelő (max. 6 számjegy, 4 tizedes).")
    private BigDecimal detectionLimit;

    @DecimalMin(value = "0.0", message = "A mérési bizonytalanság nem lehet negatív.")
    @DecimalMax(value = "100.0", message = "A mérési bizonytalanság legfeljebb 100 lehet.")
    @Digits(integer = 3, fraction = 2, message = "A bizonytalanság formátuma nem megfelelő (max. 3 számjegy, 2 tizedes).")
    private BigDecimal measurementUncertainty;

    private String analysisMethod;

    @NotNull(message = "A laborjelentés azonosítója kötelező.")
    private Long labReportId;

    @PastOrPresent(message = "Az elemzés dátuma nem lehet jövőbeli.")
    private LocalDateTime analysisDate;

    @Override
    public String toString() {
        return "SampleAnalyticalResultRequestDTO{" +
                "sampleContaminantId=" + sampleContaminantId +
                ", resultMain=" + resultMain +
                ", resultControl=" + resultControl +
                ", resultMainControl=" + resultMainControl +
                ", resultMeasurementUnitId=" + resultMeasurementUnitId +
                ", isBelowDetectionLimit=" + isBelowDetectionLimit +
                ", detectionLimit=" + detectionLimit +
                ", measurementUncertainty=" + measurementUncertainty +
                ", analysisMethod='" + analysisMethod + '\'' +
                ", labReportId=" + labReportId +
                ", analysisDate=" + analysisDate +
                '}';
    }
}

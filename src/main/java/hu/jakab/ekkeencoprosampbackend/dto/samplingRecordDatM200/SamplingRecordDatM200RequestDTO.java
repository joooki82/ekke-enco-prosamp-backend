package hu.jakab.ekkeencoprosampbackend.dto.samplingRecordDatM200;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SamplingRecordDatM200RequestDTO {

    @NotNull(message = "A mintavétel dátuma kötelező.")
    @PastOrPresent(message = "A mintavétel dátuma nem lehet jövőbeli.")
    private LocalDateTime samplingDate;

    @NotNull(message = "A mintavevő felhasználó azonosítója kötelező.")
    private UUID conductedById;

    @NotNull(message = "A cég azonosítója kötelező.")
    private Long companyId;

    @NotNull(message = "A helyszín azonosítója kötelező.")
    private Long siteLocationId;

    private String testedPlant;

    private String technology;

    @Min(value = 1, message = "A műszakok száma és hossza pozitív kell legyen.")
    private Integer shiftCountAndDuration;

    @Min(value = 1, message = "A műszakonkénti dolgozók száma pozitív kell legyen.")
    private Integer workersPerShift;

    @NotNull(message = "Az expozíciós idő megadása kötelező.")
    @Min(value = 1, message = "Az expozíciós idő pozitív szám kell legyen.")
    private Long exposureTime;

    @Digits(integer = 3, fraction = 2, message = "A hőmérséklet formátuma nem megfelelő.")
    private BigDecimal temperature;

    @DecimalMin(value = "0.0", message = "A páratartalom nem lehet negatív.")
    @DecimalMax(value = "100.0", message = "A páratartalom legfeljebb 100 lehet.")
    @Digits(integer = 3, fraction = 2, message = "A páratartalom formátuma nem megfelelő.")
    private BigDecimal humidity;

    @Digits(integer = 3, fraction = 2, message = "A szélsebesség formátuma nem megfelelő.")
    private BigDecimal windSpeed;

    @Digits(integer = 5, fraction = 2, message = "A nyomás1 formátuma nem megfelelő.")
    private BigDecimal pressure1;

    @Digits(integer = 5, fraction = 2, message = "A nyomás2 formátuma nem megfelelő.")
    private BigDecimal pressure2;

    @Size(max = 255, message = "Az egyéb környezeti feltételek legfeljebb 255 karakter lehet.")
    private String otherEnvironmentalConditions;

    private String airFlowConditions;

    private String operationMode;

    private String operationBreak;

    private String localAirExtraction;

    private String serialNumbersOfSamples;

    @NotNull(message = "A projekt azonosító megadása kötelező.")
    private Long projectId;

    @NotNull(message = "A státusz megadása kötelező.")
    private String status;

    private String remarks;

    private List<Long> equipmentIds;
}

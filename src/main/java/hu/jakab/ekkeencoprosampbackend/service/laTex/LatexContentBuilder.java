package hu.jakab.ekkeencoprosampbackend.service.laTex;

import hu.jakab.ekkeencoprosampbackend.model.*;
import hu.jakab.ekkeencoprosampbackend.service.TestReportService;
import hu.jakab.ekkeencoprosampbackend.service.utils.SignificantFiguresUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class LatexContentBuilder {

    private static final Logger logger = LoggerFactory.getLogger(TestReportService.class);


    public String formatDateInHungarian(LocalDate date) {
        return date.getYear() + ". " +
                date.getMonth().getDisplayName(TextStyle.FULL, new Locale("hu")) + " " +
                date.getDayOfMonth() + ".";
    }

    public String generateSamplersList(List<TestReportSampler> samplers) {
        StringBuilder samplersTable = new StringBuilder(); // Create new instance every time the method is invoked

        for (TestReportSampler sampler : samplers) {
            samplersTable.append("& ")
                    .append(sampler.getUser().getUsername())
                    .append(", ")
                    .append(sampler.getUser().getRole())
                    .append(" \\\\ ");
        }

        return samplersTable.toString(); // Convert to a string before returning
    }

    public String generateClientContact(Client client) {
        if (client == null) {
            return "";
        }
        return "& " + client.getName() + " \\\\  & " + client.getPhone();
    }

    public String generateSamplingSchedule(TestReport testReport) {
        if (testReport == null || testReport.getSamplingRecord() == null) {
            return "No sampling record available.";
        }

        List<Sample> samples = testReport.getSamplingRecord().getSamples();
        if (samples.isEmpty()) {
            return "No samples recorded.";
        }

        // Group samples by the date of sampling
        Map<LocalDate, List<Sample>> groupedByDate = samples.stream()
                .collect(Collectors.groupingBy(sample -> sample.getStartTime().toLocalDate()));

        // Generate formatted schedule
        StringBuilder schedule = new StringBuilder();

        groupedByDate.forEach((date, sampleList) -> {
            int minHour = sampleList.stream()
                    .mapToInt(sample -> sample.getStartTime().getHour())
                    .min().orElse(0);

            int maxHour = sampleList.stream()
                    .mapToInt(sample -> sample.getEndTime().getHour())
                    .max().orElse(0);

            // Format date in Hungarian style (e.g., "2024. április 3.")
            String formattedDate = formatDateInHungarian(date);

            schedule.append("& ")
                    .append(formattedDate)
                    .append(" & ")
                    .append(minHour)
                    .append("-")
                    .append(maxHour)
                    .append(" óra között \\\\");
        });

        return schedule.toString().trim();
    }

    public String generateSampleDetails(List<Sample> samples) {
        if (samples == null || samples.isEmpty()) {
            return "No sample data available.";

        }
        StringBuilder sampleDetails = new StringBuilder();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm"); // Fixed pattern

        for (Sample sample : samples) {
            // Extract and format data
            String formattedDate = sample.getStartTime().format(dateFormatter);
            String formattedStartTime = sample.getStartTime().format(timeFormatter)
                    .replace(":", "\\textsuperscript{") + "}"; // Fix LaTeX superscript
            String formattedEndTime = sample.getEndTime().format(timeFormatter)
                    .replace(":", "\\textsuperscript{") + "}";

            // Collect contaminant groups into a comma-separated string
            String contaminantGroups = sample.getSampleContaminants().stream()
                    .map(SampleContaminant::getContaminant)
                    .map(Contaminant::getContaminantGroup)
                    .distinct()
                    .map(ContaminantGroup::getName)
                    .collect(Collectors.joining(", "));


            sampleDetails.append("\\begin{minipage}{3.5cm} ")
                    .append("\\centering \\vspace{3pt} ")
                    .append("\\textbf{").append(sample.getSampleIdentifier())
                    .append(" /} \\\\ \\textit{")
                    .append(contaminantGroups).append("} \\vspace{3pt}")
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{2cm} ")
                    .append("\\centering ")
                    .append(formattedDate).append("\\\\ ")
                    .append(formattedStartTime)
                    .append(" - ")
                    .append(formattedEndTime)
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{3.5cm} ")
                    .append("\\centering \\vspace{3pt}  ")
                    .append(sample.getLocation())
                    .append("\\end{minipage} & ")
                    .append("\\begin{minipage}{2cm} \\centering ")
                    .append(sample.getEmployeeName() != null ? sample.getEmployeeName() : "-")
                    .append(" \\end{minipage} & ")
                    .append("\\begin{minipage}{1cm} \\centering ")
                    .append(sample.getTemperature() != null ? sample.getTemperature() : "-")
                    .append(" \\end{minipage} & ")
                    .append("\\begin{minipage}{1cm} \\centering ")
                    .append(sample.getHumidity() != null ? sample.getHumidity() : "-")
                    .append(" \\end{minipage} \\\\ ")
                    .append("\\hline");

        }

        return sampleDetails.toString();
    }

    public String formatBigDecimal(BigDecimal value, int scale) {
        if (value == null) return "-"; // Return placeholder if value is missing
        return value.setScale(scale, RoundingMode.HALF_UP).toString(); // Round to 1 decimal place
    }

    public String generateEquipmentList(TestReport testReport) {

        List<Equipment> equipments = testReport.getSamplingRecord().getSamplingRecordEquipments().stream()
                .map(SamplingRecordEquipment::getEquipment)
                .toList();

        if (equipments.isEmpty()) {
            return "No equipment available.";
        }

        StringBuilder equipmentListBuilder = new StringBuilder();

        for (Equipment equipment : equipments) {
            equipmentListBuilder.append("\\begin{adjustwidth}{1cm}{0cm}\n\n")
                    .append("\t\\vspace{1.0em}")
                    .append("\t").append(equipment.getDescription()).append("\n\n")
                    .append("\t\\vspace{1.0em}")
                    .append("\t\\noindent\n")
                    .append("\t\\centering\n")
                    .append("\t\\begin{tabular}{ p{5.5cm} p{8cm} }\n");

            if (equipment.getManufacturer() != null && !equipment.getManufacturer().isEmpty()) {
                equipmentListBuilder.append("\t\tGyártó: & ").append(escapeLatex(equipment.getManufacturer())).append(" \\\\\n");
            }
            if (equipment.getMeasuringRange() != null && !equipment.getMeasuringRange().isEmpty()) {
                equipmentListBuilder.append("\t\tMéréstartomány: & ").append(escapeLatex(equipment.getMeasuringRange())).append(" \\\\\n");
            }
            if (equipment.getResolution() != null && !equipment.getResolution().isEmpty()) {
                equipmentListBuilder.append("\t\tFelbontás: & ").append(escapeLatex(equipment.getResolution())).append(" \\\\\n");
            }
            if (equipment.getAccuracy() != null && !equipment.getAccuracy().isEmpty()) {
                equipmentListBuilder.append("\t\tPontosság: & ").append(escapeLatex(equipment.getAccuracy())).append(" \\\\\n");
            }

            equipmentListBuilder.append("\t\\end{tabular}\n")
                    .append("\\end{adjustwidth}\n");
        }
//        logger.info(equipmentListBuilder.toString());

        return equipmentListBuilder.toString();
    }

    public String generateStandardList(List<Standard> standards) {
        if (standards == null || standards.isEmpty()) {
            return "\\ No standard data available. & \\ \\hline";
        }

        StringBuilder standardDetails = new StringBuilder();

        for (Standard standard : standards) {
//            logger.info(standard.toString());
            standardDetails.append(standard.getStandardNumber()).append(" & ").append(standard.getDescription()).append(" \\")
                    .append(" \\hline ");
        }

        logger.info(standardDetails.toString());

        return standardDetails.toString();
    }

    public String generateSampleResults(List<Sample> samples) {
        StringBuilder sampleResults = new StringBuilder();

        for (Sample sample : samples) {

//            String sampleIdentifier = sample.getSampleIdentifier();
//            BigDecimal sampleVolume = sample.getSampleVolumeFlowRate();

            List<SampleContaminant> sampleContaminants = sample.getSampleContaminants();

            for (SampleContaminant sampleContaminant : sampleContaminants) {

                SampleAnalyticalResult analyticalResult = sampleContaminant.getAnalyticalResult();

                if (analyticalResult != null) {

                    String contaminantName = sampleContaminant.getContaminant().getName();

                    String sampleIdentifier = sampleContaminant.getSample().getSampleIdentifier();

                    BigDecimal sampleVolume = SignificantFiguresUtil.roundToSignificantFigures(sampleContaminant.getSample().getSampleVolumeFlowRate(),3);

                    BigDecimal adsorbedAmount = SignificantFiguresUtil.roundToSignificantFigures(analyticalResult.getResultMain(),3);

                    BigDecimal concentration = SignificantFiguresUtil.roundToSignificantFigures(analyticalResult.getCalculatedConcentration(),3);

                    // Append row to LaTeX table
                    sampleResults.append("\t\\begin{minipage}{2.5cm} \\centering \\vspace{3pt} \\textbf{")
                            .append(sampleIdentifier)
                            .append("} \\vspace{3pt} \\end{minipage} &\n");

                    sampleResults.append("\t\\begin{minipage}{5cm} \\centering ")
                            .append(contaminantName)
                            .append(" \\end{minipage} &\n");

                    sampleResults.append("\t\\begin{minipage}{3cm} \\centering \\vspace{3pt} ")
                            .append(adsorbedAmount)
                            .append(" \\end{minipage} &\n");

                    sampleResults.append("\t\\begin{minipage}{2cm} \\centering ")
                            .append(sampleVolume)
                            .append(" \\end{minipage} &\n");

                    sampleResults.append("\t\\begin{minipage}{2cm} \\centering ")
                            .append(concentration)
                            .append(" \\end{minipage} \\\\\n");

                    sampleResults.append("\\hline\n");
                }
            }
        }

        // End LaTeX table
        sampleResults.append("\\end{longtable}\n");

        return sampleResults.toString();
    }


    private String escapeLatex(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("%", "\\%");
    }

}

package hu.jakab.ekkeencoprosampbackend.controller;

import hu.jakab.ekkeencoprosampbackend.service.TestReportService;
import hu.jakab.ekkeencoprosampbackend.service.utils.LaTeXReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(TestReportService.class);

    @Autowired
    private LaTeXReportService latexReportService;

    @GetMapping(value = "/generate-latex-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport() throws IOException, InterruptedException {

        logger.info("ReportController: ");

        Map<String, String> data = new HashMap<>();
        data.put("CompanyName", "Robert Bosch AS Kft.");
        data.put("Temperature", "25");
        data.put("Humidity", "60");

        logger.info("data: {}", data);


        File pdfFile = latexReportService.generatePdfReport(data);

        byte[] pdfContent = Files.readAllBytes(pdfFile.toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"latex_report.pdf\"")
                .body(pdfContent);
    }
}

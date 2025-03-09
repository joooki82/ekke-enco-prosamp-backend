package hu.jakab.ekkeencoprosampbackend.service.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import hu.jakab.ekkeencoprosampbackend.service.TestReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LaTeXReportService {

    private static final String TEMPLATE_PATH = "src\\main\\resources\\templates\\test.tex"; // Windows path
    private static final String OUTPUT_DIR = "src\\main\\resources\\reports\\";
    private static final Logger logger = LoggerFactory.getLogger(TestReportService.class);

    public File generatePdfReport(Map<String, String> data) throws IOException, InterruptedException {
        // Read LaTeX template
        String content = new String(Files.readAllBytes(Paths.get(TEMPLATE_PATH)));
        logger.info("content: {}", content);

        // Replace placeholders
        for (Map.Entry<String, String> entry : data.entrySet()) {
            content = content.replace("\\VAR" + entry.getKey() + "{}", entry.getValue());
        }

        // Write updated content to new .tex file
        String texFileName = OUTPUT_DIR + "generated_report.tex";
        Files.write(Paths.get(texFileName), content.getBytes());
        logger.info("texFileName: {}", texFileName);


        // Run LaTeX compiler (pdflatex) on Windows
        ProcessBuilder pb = new ProcessBuilder(
            "cmd.exe", "/c", "pdflatex", "-output-directory", OUTPUT_DIR, texFileName
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        process.waitFor();

        return new File(OUTPUT_DIR + "generated_report.pdf");
    }
}

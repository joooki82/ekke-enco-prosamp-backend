package hu.jakab.ekkeencoprosampbackend.service.laTex;

import hu.jakab.ekkeencoprosampbackend.exception.CustomFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class LaTeXReportService {

    private static final String TEMPLATE_PATH = "templates/test_report_template_placeholder.tex";
    private static final String OUTPUT_DIR = "src/main/resources/reports/";
    private static final Logger logger = LoggerFactory.getLogger(LaTeXReportService.class);

    public byte[] generatePdfReport(Map<String, String> data) throws IOException, InterruptedException {

        ClassPathResource resource = new ClassPathResource(TEMPLATE_PATH);
        logger.info(resource.toString());

        try (InputStream inputStream = resource.getInputStream()) {


            String content = new String(inputStream.readAllBytes());
            logger.info("Read LaTeX template successfully.");


            for (Map.Entry<String, String> entry : data.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }


            Files.createDirectories(Paths.get(OUTPUT_DIR));


            String texFileName = OUTPUT_DIR + "generated_report.tex";
            Files.write(Paths.get(texFileName), content.getBytes());
            logger.info("Written modified LaTeX file: {}", texFileName);


            ProcessBuilder pb = new ProcessBuilder(
                    "cmd.exe", "/c", "pdflatex", "-interaction=nonstopmode",
                    "-output-directory=" + OUTPUT_DIR, texFileName
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info("LaTeX Compiler: {}", line);
                }
            }

            int exitCode = process.waitFor();
            logger.info("pdflatex process exited with code: {}", exitCode);


            Path pdfPath = Paths.get(OUTPUT_DIR + "generated_report.pdf");
            if (!Files.exists(pdfPath)) {
                logger.error("PDF file was not created!");
                throw new IOException("Failed to generate PDF file.");
            }


            byte[] pdfBytes = Files.readAllBytes(pdfPath);

            logger.info("PDF Report generated successfully.");
            return pdfBytes;

        } catch (FileNotFoundException e) {
            logger.error("Template file not found: {}", e.getMessage());
            throw new CustomFileNotFoundException("LaTeX template is missing. Please upload or restore it.", e);
        } catch (IOException | InterruptedException e) {
            logger.error("Error during LaTeX report generation: {}", e.getMessage());
            throw new RuntimeException("Failed to generate the report", e);
        }

    }
}

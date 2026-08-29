package com.rag.ragApp.service.sources.lawSchoolBooks;

import com.rag.ragApp.entity.LawBookEntity;
import com.rag.ragApp.pojos.LawBookPojo;
import com.rag.ragApp.repository.LawBookRepository;
import com.rag.ragApp.service.EmbeddingService;
import com.rag.ragApp.service.agents.BookPdfToJsonAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FetchDataPDFs {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private LawBookRepository lawBookRepository;

    @Autowired
    private BookPdfToJsonAgent bookPdfToJsonAgent;

    public LawBookEntity fetchDataFromSource(String path) {
        try (Stream<Path> pathStream = Files.walk(Paths.get(path))){
            List<Path> pdfFiles = pathStream
                    .filter(p -> p.toString().endsWith(".pdf"))
                    .collect(Collectors.toList());

            for (Path file : pdfFiles) {
                // Convert Path → File
                File pdfFile = file.toFile();

                try (PDDocument doc = Loader.loadPDF(pdfFile)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    stripper.setLineSeparator(" ");

                    String text = stripper.getText(doc);
                    String value = fetchPaperModule(text);

//                    LawBookPojo lawBookPojo = bookPdfToJsonAgent.runBookPdfToJsonAgent(text);

                    log.info("Content - {}", text);
                    log.info("Module And Paper - {}", value);
                    LawBookEntity lawBookEntity = new LawBookEntity();
                    lawBookEntity.setChapter(value);
                    lawBookEntity.setContent(text);
                    saveToDb(lawBookEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String fetchPaperModule(String text) {
        String value = "";

        int start = text.indexOf("LAW");
        if (start != -1) {
            start = start + "LAW".length();

            int end = text.indexOf("\r\n", start);
            if (end == -1) {
                end = text.indexOf("\n", start);
            }

            if (end != -1) {
                value = text.substring(start, end).trim();
            } else {
                value = text.substring(start).trim();
            }
        }
        return value;
    }

    private void saveToDb(LawBookEntity lawBookEntity) {
        String text = lawBookEntity.getContent();
        log.debug("Embedding Text - {}", text);
        float[] embedding = embeddingService.generateEmbedding(
                text
        );
        lawBookEntity.setEmbedding(embedding);
        lawBookRepository.save(lawBookEntity);
    }

    public Object parseDataAndReturnRequiredObject(String html) {
        return null;
    }
}

package com.rag.ragApp.controller;
import com.rag.ragApp.service.DataIngestionService;
import com.rag.ragApp.service.MultiAgentRagsCrew;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RagController {

    private final DataIngestionService ingestionService;
    private final MultiAgentRagsCrew multiAgentCrew;

    public RagController(DataIngestionService ingestionService, MultiAgentRagsCrew multiAgentCrew) {
        this.ingestionService = ingestionService;
        this.multiAgentCrew = multiAgentCrew;
    }

    // Endpoint 1: Populate your Vector Store with context documents
    @PostMapping("/ingest")
    public String loadKnowledgeBase(@RequestBody String textContent) {
        ingestionService.ingestRawText(textContent);
        return "Knowledge successfully ingested into pgvector database.";
    }

    // Endpoint 2: Execute the Researcher + Writer multi-agent crew execution loop
    @GetMapping("/crew/execute")
    public String executeCrewWorkflow(@RequestParam(value = "objective") String objective) {
        return multiAgentCrew.runCrewWorkflow(objective);
    }
}

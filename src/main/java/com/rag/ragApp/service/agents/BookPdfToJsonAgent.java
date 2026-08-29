package com.rag.ragApp.service.agents;

import com.rag.ragApp.pojos.LawBookPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookPdfToJsonAgent {
    @Autowired
    private ChatClient.Builder chatClientBuilder;

    public LawBookPojo runBookPdfToJsonAgent(String text){
        String prompt = """
            Fetch paper, module, reviewer, writer, coordinator, investigator.
            Don't send anything other than json in the response

            Text:
            """ + text;

        LawBookPojo response = chatClientBuilder.build().prompt()
                .user(prompt)
                .system("You are a strict data-extraction agent. Do NOT write conversational introductions, explanations, or text like 'Here is your JSON'. Output ONLY raw, valid JSON.")
                .call()
                .entity(LawBookPojo.class);
        log.info("response.toString() - {}",response.toString());
        return response;
    }
}

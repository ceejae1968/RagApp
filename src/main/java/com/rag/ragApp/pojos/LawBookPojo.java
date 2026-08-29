package com.rag.ragApp.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LawBookPojo {

    private String paper;          // e.g. "Constitution of India"
    private String module;            // e.g. "Fundamental Rights"
    private String reviewer;            // e.g. "Fundamental Rights"
    private String writer;            // e.g. "Fundamental Rights"
    private String coordinator;            // e.g. "Fundamental Rights"
    private String investigator;            // e.g. "Fundamental Rights"
}

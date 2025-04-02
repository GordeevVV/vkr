package com.unn.regex.dto;

import lombok.Data;

import java.util.List;

@Data
public class TompsonDefaultDTO {
    private List<String> q;
    private List<String> sigma;
    private List<String> f;
    private String q0;
    private List<Rule> rules;
}

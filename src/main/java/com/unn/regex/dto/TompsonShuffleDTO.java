package com.unn.regex.dto;

import lombok.Data;

import java.util.List;

@Data
public class TompsonShuffleDTO {
    private List<String> language1;
    private List<String> language2;
    private String n1Start;
    private String n2Start;
    private List<String> sigma;
    private List<String> finalStates1;
    private List<String> finalState2;
    private List<Rule> rules1;
    private List<Rule> rules2;
}

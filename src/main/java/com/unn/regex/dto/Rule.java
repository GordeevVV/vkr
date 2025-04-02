package com.unn.regex.dto;

import lombok.Data;

@Data
public class Rule {
    private String state;
    private String term;
    private String nextState;
}

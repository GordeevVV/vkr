package com.unn.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VkrApplication {

    public static void main(String[] args) {

//        String[] testCases = {
//                "a", "ε", "∅",
//                "a**", "a++", "a??",
//                "(a*)*", "(a+)+", "(a?)?*",
//                "a|a", "a|b", "a|ε", "ε|a", "a|∅", "∅|a",
//                "a·ε", "ε·a", "a·∅", "∅·a", "a·b",
//                "a*", "(a|b)*", "(a*)*", "(a+)*",
//                "(a)", "(a|b)", "((a))", "(a|b)|c",
//                "a*·b*", "(a|b)·(c|d)", "(a|ε)·b", "(a|b)*·c",
//                "", "∅|∅", "ε|ε", "a|b|c|a", "(a|b)|(b|a)",
//                "a*b*", "(a|b)*c", "a*(b|c)*", "(a|ε)(b|ε)"
//        };
//
//        for (String testCase : testCases) {
//            String simplified = RegexUtils.simplify(testCase);
//            System.out.println("Упрощение '" + testCase + "' → '" + simplified + "'");
//        }
        SpringApplication.run(VkrApplication.class, args);


    }

}

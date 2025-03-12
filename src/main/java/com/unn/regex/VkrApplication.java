package com.unn.regex;

import com.unn.regex.entities.FiniteStateAutomata;
import com.unn.regex.utils.RegexUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class VkrApplication {

    public static void main(String[] args) throws IOException {
        var ndfsa = new FiniteStateAutomata(List.of("S0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "qf"),
                List.of("1", "0", "+", "2"),
                List.of("qf"),
                "S0", "x");
        ndfsa.AddRule("S0", "1", "1");
        ndfsa.AddRule("1", "0", "2");
        ndfsa.AddRule("2", "+", "3");

        ndfsa.AddRule("3", "", "4");
        ndfsa.AddRule("4", "", "5");
        ndfsa.AddRule("4", "", "7");
        ndfsa.AddRule("4", "", "9");
        ndfsa.AddRule("5", "1", "6");
        ndfsa.AddRule("7", "2", "8");
        ndfsa.AddRule("6", "", "9");
        ndfsa.AddRule("8", "", "9");
        ndfsa.AddRule("9", "", "4");
        ndfsa.AddRule("9", "", "10");

        ndfsa.AddRule("10", "1", "11");
        ndfsa.AddRule("11", "0", "12");
        ndfsa.AddRule("12", "", "13");
        ndfsa.AddRule("13", "", "9");
        ndfsa.AddRule("13", "", "14");

        ndfsa.AddRule("14", "", "15");
        ndfsa.AddRule("14", "", "17");
        ndfsa.AddRule("15", "0", "16");
        ndfsa.AddRule("17", "1", "18");
        ndfsa.AddRule("16", "", "19");
        ndfsa.AddRule("18", "", "19");
        ndfsa.AddRule("19", "", "14");
        ndfsa.AddRule("19", "", "20");
        ndfsa.AddRule("20", "", "15");
        ndfsa.AddRule("14", "", "qf");
        ndfsa.AddRule("20", "", "qf");

        var dka = new FiniteStateAutomata();
        dka.BuildDeltaDKAutomate(ndfsa);
        dka.DebugAuto();
        System.out.println("Enter line to execute :");
        Scanner scanner = new Scanner(System.in);
        String sin = scanner.nextLine();
        dka.Execute(sin);

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

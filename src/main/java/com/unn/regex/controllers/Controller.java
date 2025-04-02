package com.unn.regex.controllers;

import com.unn.regex.dto.TompsonDefaultDTO;
import com.unn.regex.dto.TompsonShuffleDTO;
import com.unn.regex.entities.FiniteStateAutomata;
import com.unn.regex.entities.Symbol;
import com.unn.regex.entities.TompsonShuffle;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class Controller {

    @GetMapping("/shuffle")
    public ResponseEntity<?> getShuffle(@RequestBody TompsonShuffleDTO tompsonShuffleDTO) {
        TompsonShuffle shuffle = new TompsonShuffle(tompsonShuffleDTO.getSigma().stream().map(Symbol::stringToSymbol).collect(Collectors.toList()),
                tompsonShuffleDTO.getFinalStates1().stream().map(Symbol::stringToSymbol).collect(Collectors.toList()),
                tompsonShuffleDTO.getLanguage1().stream().map(Symbol::stringToSymbol).collect(Collectors.toList()),
                tompsonShuffleDTO.getFinalState2().stream().map(Symbol::stringToSymbol).collect(Collectors.toList()),
                tompsonShuffleDTO.getLanguage2().stream().map(Symbol::stringToSymbol).collect(Collectors.toList()));
        shuffle.addRules(tompsonShuffleDTO.getRules1(), tompsonShuffleDTO.getRules2());

        return new ResponseEntity<>(shuffle.buildShuffleDFA(Symbol.stringToSymbol(tompsonShuffleDTO.getN1Start()),
                Symbol.stringToSymbol(tompsonShuffleDTO.getN2Start())), HttpStatus.OK);
    }

    @GetMapping("/nfatodfa")
    public ResponseEntity<?> getDfa(@RequestBody TompsonDefaultDTO tompsonDefaultDTO) {
        var ndfsa = new FiniteStateAutomata(tompsonDefaultDTO.getQ(),
                tompsonDefaultDTO.getSigma(),
                tompsonDefaultDTO.getF(),
                tompsonDefaultDTO.getQ0());
        ndfsa.addRules(tompsonDefaultDTO.getRules());

        var dka = new FiniteStateAutomata();
        dka.BuildDeltaDKAutomate(ndfsa);
        dka.DebugAuto();
        System.out.println("Enter line to execute :");
        Scanner scanner = new Scanner(System.in);
        String sin = scanner.nextLine();
        dka.Execute(sin);
        return new ResponseEntity<>(dka.deltaD, HttpStatus.OK);
    }


//    var ndfsa = new FiniteStateAutomata(List.of("S0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "qf"),
//            List.of("1", "0", "+", "2"),
//            List.of("qf"),
//            "S0", "x");
//        ndfsa.AddRule("S0", "1", "1");
//        ndfsa.AddRule("1", "0", "2");
//        ndfsa.AddRule("2", "+", "3");
//
//        ndfsa.AddRule("3", "", "4");
//        ndfsa.AddRule("4", "", "5");
//        ndfsa.AddRule("4", "", "7");
//        ndfsa.AddRule("4", "", "9");
//        ndfsa.AddRule("5", "1", "6");
//        ndfsa.AddRule("7", "2", "8");
//        ndfsa.AddRule("6", "", "9");
//        ndfsa.AddRule("8", "", "9");
//        ndfsa.AddRule("9", "", "4");
//        ndfsa.AddRule("9", "", "10");
//
//        ndfsa.AddRule("10", "1", "11");
//        ndfsa.AddRule("11", "0", "12");
//        ndfsa.AddRule("12", "", "13");
//        ndfsa.AddRule("13", "", "9");
//        ndfsa.AddRule("13", "", "14");
//
//        ndfsa.AddRule("14", "", "15");
//        ndfsa.AddRule("14", "", "17");
//        ndfsa.AddRule("15", "0", "16");
//        ndfsa.AddRule("17", "1", "18");
//        ndfsa.AddRule("16", "", "19");
//        ndfsa.AddRule("18", "", "19");
//        ndfsa.AddRule("19", "", "14");
//        ndfsa.AddRule("19", "", "20");
//        ndfsa.AddRule("20", "", "15");
//        ndfsa.AddRule("14", "", "qf");
//        ndfsa.AddRule("20", "", "qf");
//
//    var dka = new FiniteStateAutomata();
//        dka.BuildDeltaDKAutomate(ndfsa);
//        dka.DebugAuto();
//        System.out.println("Enter line to execute :");
//    Scanner scanner = new Scanner(System.in);
//    String sin = scanner.nextLine();
//        dka.Execute(sin);
}

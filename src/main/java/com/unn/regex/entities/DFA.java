package com.unn.regex.entities;

import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
public class DFA {
    Map<Set<State>, Map<Character, Set<State>>> transitionTable;
    Set<Set<State>> acceptStates;
    Set<State> startState;

    public DFA(Set<State> startState, Map<Set<State>, Map<Character, Set<State>>> transitionTable, Set<Set<State>> acceptStates) {
        this.startState = startState;
        this.transitionTable = transitionTable;
        this.acceptStates = acceptStates;
    }

    // Проверка, принимает ли ДКА строку
    boolean accepts(String input) {
        Set<State> currentState = startState;
        for (char symbol : input.toCharArray()) {
            currentState = transitionTable.get(currentState).get(symbol);
            if (currentState == null) return false;
        }
        return acceptStates.contains(currentState);
    }

    // Вывод ДКА в понятном виде
    public void printDFA() {
        System.out.println("ДКА:");
        System.out.println("Начальное состояние: " + startState);
        System.out.println("Принимающие состояния: " + acceptStates);
        System.out.println("Переходы ДКА:");
        for (Map.Entry<Set<State>, Map<Character, Set<State>>> entry : transitionTable.entrySet()) {
            Set<State> fromState = entry.getKey();
            for (Map.Entry<Character, Set<State>> transition : entry.getValue().entrySet()) {
                char symbol = transition.getKey();
                Set<State> toState = transition.getValue();
                System.out.println(fromState + " --(" + symbol + ")--> " + toState);
            }
        }
    }
}
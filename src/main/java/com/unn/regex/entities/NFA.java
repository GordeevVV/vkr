package com.unn.regex.entities;


import lombok.Data;

import java.util.*;

@Data
public class NFA {
    State start;
    State accept;

    NFA(State start, State accept) {
        this.start = start;
        this.accept = accept;
    }

    // Проверка, принимает ли НКА строку
    boolean accepts(String input) {
        Set<State> currentStates = epsilonClosure(Collections.singleton(start));
        for (char symbol : input.toCharArray()) {
            currentStates = epsilonClosure(move(currentStates, symbol));
        }
        return currentStates.contains(accept);
    }

    // Эпсилон-замыкание
    Set<State> epsilonClosure(Set<State> states) {
        Set<State> closure = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State state = stack.pop();
            for (State nextState : state.getTransitions('\0')) { // '\0' — эпсилон-переход
                if (!closure.contains(nextState)) {
                    closure.add(nextState);
                    stack.push(nextState);
                }
            }
        }
        return closure;
    }

    // Переход по символу
    Set<State> move(Set<State> states, char symbol) {
        Set<State> result = new HashSet<>();
        for (State state : states) {
            result.addAll(state.getTransitions(symbol));
        }
        return result;
    }

    // Вывод НКА в понятном виде
    void printNFA() {
        System.out.println("НКА:");
        System.out.println("Начальное состояние: " + start);
        System.out.println("Принимающее состояние: " + accept);
        System.out.println("Переходы:");
        Set<State> visited = new HashSet<>();
        Stack<State> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            State current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                for (Map.Entry<Character, Set<State>> entry : current.getTransitions().entrySet()) {
                    char symbol = entry.getKey();
                    for (State nextState : entry.getValue()) {
                        System.out.println(current + " --(" + (symbol == '\0' ? "ε" : symbol) + ")--> " + nextState);
                        stack.push(nextState);
                    }
                }
            }
        }
    }
}
package com.unn.regex.services;

import com.unn.regex.entities.DFA;
import com.unn.regex.entities.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DFABuilder {
    private final RegexDerivative regexDerivative;

    public DFABuilder(RegexDerivative regexDerivative) {
        this.regexDerivative = regexDerivative;
    }

    public DFA buildDFAFromRegex(String regex) {
        // Алфавит (в данном примере — a и b)
        char[] alphabet = {'a', 'b'};

        // Таблица переходов ДКА
        Map<Set<State>, Map<Character, Set<State>>> transitionTable = new HashMap<>();
        // Множество принимающих состояний ДКА
        Set<Set<State>> acceptStates = new HashSet<>();
        // Очередь для обработки состояний ДКА
        Queue<Set<State>> queue = new LinkedList<>();

        // Начальное состояние ДКА
        Set<State> startState = new HashSet<>();
        startState.add(new State(0, regex));
        queue.add(startState);

        while (!queue.isEmpty()) {
            Set<State> currentState = queue.poll();
            transitionTable.put(currentState, new HashMap<>());

            // Проверка, является ли текущее состояние принимающим
            for (State state : currentState) {
                if (regexDerivative.containsEpsilon(state.getRegex())) {
                    acceptStates.add(currentState);
                    break;
                }
            }

            // Построение переходов для каждого символа алфавита
            for (char symbol : alphabet) {
                Set<State> nextState = new HashSet<>();
                for (State state : currentState) {
                    String derivative = regexDerivative.derivative(state.getRegex(), symbol);
                    if (!derivative.isEmpty()) {
                        nextState.add(new State(state.getId() + 1, derivative));
                    }
                }
                if (!nextState.isEmpty()) {
                    transitionTable.get(currentState).put(symbol, nextState);
                    if (!transitionTable.containsKey(nextState)) {
                        queue.add(nextState);
                    }
                }
            }
        }
        return new DFA(startState, transitionTable, acceptStates);
    }
}

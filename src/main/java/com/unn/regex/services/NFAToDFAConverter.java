//package com.unn.regex.services;
//
//import com.unn.regex.entities.DFA;
//import com.unn.regex.entities.NFA;
//import com.unn.regex.entities.State;
//import com.unn.regex.entities.ThompsonNFA;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//
//public class NFAToDFAConverter {
//    private final NFA nfa;
//    private final Set<Character> alphabet;
//
//    public NFAToDFAConverter(NFA nfa) {
//        this.nfa = nfa;
//        this.alphabet = collectAlphabet();
//    }
//
//    // Сбор символов алфавита (исключая ε)
//    private Set<Character> collectAlphabet() {
//        Set<Character> symbols = new HashSet<>();
//        Stack<State> stack = new Stack<>();
//        Set<State> visited = new HashSet<>();
//
//        stack.push(nfa.getStart());
//        visited.add(nfa.getStart());
//
//        while (!stack.isEmpty()) {
//            State current = stack.pop();
//            for (Map.Entry<Character, Set<State>> entry : current.getTransitions().entrySet()) {
//                char c = entry.getKey();
//                if (c != '\0') symbols.add(c);
//                for (State next : entry.getValue()) {
//                    if (!visited.contains(next)) {
//                        visited.add(next);
//                        stack.push(next);
//                    }
//                }
//            }
//        }
//        return symbols;
//    }
//
//    // Эпсилон-замыкание
//    private Set<State> epsilonClosure(Set<State> states) {
//        Set<State> closure = new HashSet<>(states);
//        Stack<State> stack = new Stack<>();
//        stack.addAll(states);
//
//        while (!stack.isEmpty()) {
//            State s = stack.pop();
//            for (State next : s.getTransitions().getOrDefault('\0', new HashSet<>())) {
//                if (!closure.contains(next)) {
//                    closure.add(next);
//                    stack.push(next);
//                }
//            }
//        }
//        return closure;
//    }
//
//    // Переход по символу
//    private Set<State> move(Set<State> states, char symbol) {
//        Set<State> result = new HashSet<>();
//        for (State s : states) {
//            result.addAll(s.getTransitions().getOrDefault(symbol, new HashSet<>()));
//        }
//        return result;
//    }
//
//    // Преобразование НКА в ДКА
//    public DFA convert() {
//        Map<Set<State>, Map<Character, Set<State>>> dfaTrans = new HashMap<>();
//        Set<Set<State>> dfaAccept = new HashSet<>();
//        Queue<Set<State>> queue = new LinkedList<>();
//
//        // Начальное состояние ДКА
//        Set<State> startClosure = epsilonClosure(Collections.singleton(nfa.getStart()));
//        queue.add(startClosure);
//        dfaTrans.put(startClosure, new HashMap<>());
//
//        while (!queue.isEmpty()) {
//            Set<State> current = queue.poll();
//
//            // Проверка на принимающее состояние
//            if (current.stream().anyMatch(s -> s.isAccept())) {
//                dfaAccept.add(current);
//            }
//
//            // Обработка переходов по символам
//            for (char c : alphabet) {
//                Set<State> nextStates = move(current, c);
//                Set<State> nextClosure = epsilonClosure(nextStates);
//                if (nextClosure.isEmpty()) continue;
//
//                if (!dfaTrans.containsKey(nextClosure)) {
//                    dfaTrans.put(nextClosure, new HashMap<>());
//                    queue.add(nextClosure);
//                }
//
//                dfaTrans.get(current).put(c, nextClosure);
//            }
//        }
//
//        return new DFA(startClosure, dfaTrans, dfaAccept);
//    }
//    public static void main(String[] args) {
//        // Построение НКА для регулярного выражения "a*b"
//        NFA nfa = ThompsonNFA.build("a*b");
//
//        // Преобразование НКА в ДКА
//        NFAToDFAConverter converter = new NFAToDFAConverter(nfa);
//        DFA dfa = converter.convert();
//
//        // Вывод ДКА
//        dfa.printDFA();
//    }
//}
//
//

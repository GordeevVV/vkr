package com.unn.regex.entities;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TompsonShuffle {
    public List<Symbol> Q = null; ///< множество состояний
    public List<Symbol> sigma = null; ///< множество алфавит
    public List<DeltaQSigma> delta = null;  ///< множество правил перехода
    public Symbol Q0 = null; ///< начальное состояние
    public List<Symbol> F = null;
    public List<Symbol> config = new ArrayList<>();
    public List<DeltaQSigma> deltaD = new ArrayList<>();

    List<DeltaQSigma> n1Delta = new ArrayList<>();

    List<Symbol> n1FinalStates = new ArrayList<>();
    Set<Symbol> n1Alphabet = new HashSet<>();

    List<DeltaQSigma> n2Delta = new ArrayList<>();

    List<Symbol> n2FinalStates = new ArrayList<>();
    Set<Symbol> n2Alphabet = new HashSet<>();

    List<Symbol> finalStates = new ArrayList<>();///< множество конечных состояний

    public TompsonShuffle() {
    }

    public TompsonShuffle(List<Symbol> Q, List<Symbol> Sigma, List<Symbol> F, Symbol q0) {
        this.Q = Q;
        this.sigma = Sigma;
        this.Q0 = q0;
        this.F = F;
        this.delta = new ArrayList<>();
    }

    public void AddRule(String state, String term, String nextState) {
        this.delta.add(new DeltaQSigma(Symbol.stringToSymbol(state),
                Symbol.stringToSymbol(term), List.of(new Symbol(nextState))));
    }

    public List<Symbol> buildShuffleDFA(Symbol n1Start, Symbol n2Start) {
        List<List<StatePair>> queue = new ArrayList<>();
        List<StatePair> newStates;

        // Инициализация начальным состоянием как ε-closure обоих автоматов
        List<Symbol> start1Cl = epsClosureN1(List.of(n1Start));
        List<Symbol> start2Cl = epsClosureN2(List.of(n2Start));

        List<StatePair> initialState = new ArrayList<>();
        for (Symbol s1 : start1Cl) {
            for (Symbol s2 : start2Cl) {
                initialState.add(new StatePair(s1, s2));
            }
        }

        queue.add(initialState);
        config.add(Symbol.stringToSymbol(setName(initialState)));

        while (!queue.isEmpty()) {
            List<StatePair> currStatePairs = epsClosurePair(queue.remove(0));

            for (Symbol a : sigma) {
                List<StatePair> newStatesN1 = new ArrayList<>();
                List<StatePair> newStatesN2 = new ArrayList<>();

                // Обработка переходов через N1
                if (n1Alphabet.contains(a)) {
                    for (StatePair pair : currStatePairs) {
                        List<Symbol> q1Trans = moveN1(pair.q1, a);
                        List<Symbol> q1Closure = epsClosureN1(q1Trans);
                        for (Symbol s1 : q1Closure) {
                            newStatesN1.add(new StatePair(s1, pair.q2));
                        }
                    }
                }

                // Обработка переходов через N2
                if (n2Alphabet.contains(a)) {
                    for (StatePair pair : currStatePairs) {
                        List<Symbol> q2Trans = moveN2(pair.q2, a);
                        List<Symbol> q2Closure = epsClosureN2(q2Trans);
                        for (Symbol s2 : q2Closure) {
                            newStatesN2.add(new StatePair(pair.q1, s2));
                        }
                    }
                }

                // Комбинируем результаты и удаляем дубликаты
                Set<StatePair> combined = new HashSet<>();
                combined.addAll(newStatesN1);
                combined.addAll(newStatesN2);
                newStates = new ArrayList<>(combined);

                // Проверка и добавление нового состояния
                String newStateName = setName(newStates);
                Symbol newStateSymbol = Symbol.stringToSymbol(newStateName);

                if (!config.contains(newStateSymbol)) {
                    queue.add(newStates);
                    config.add(newStateSymbol);
                }

                // Добавление перехода
                if (!newStates.isEmpty()) {
                    DeltaQSigma deltaVar = new DeltaQSigma(
                            Symbol.stringToSymbol(setName(currStatePairs)),
                            Symbol.stringToSymbol(a.getSymbol()),
                            List.of(newStateSymbol)
                    );
                    deltaD.add(deltaVar);
                }
            }
        }

        // Определение финальных состояний
        for (Symbol state : config) {
            List<StatePair> pairs = parseState(state);
            for (StatePair pair : pairs) {
                if (n1FinalStates.contains(pair.q1) && n2FinalStates.contains(pair.q2)) {
                    finalStates.add(state);
                    break;
                }
            }
        }
        return finalStates;
    }

    // Вспомогательные методы
    private List<StatePair> epsClosurePair(List<StatePair> state) {
        Set<StatePair> closure = new HashSet<>();
        for (StatePair pair : state) {
            closure.addAll(epsClosurePair(pair));
        }
        return new ArrayList<>(closure);
    }

    private List<StatePair> epsClosurePair(StatePair pair) {
        List<Symbol> q1Closure = epsClosureN1(List.of(pair.q1));
        List<Symbol> q2Closure = epsClosureN2(List.of(pair.q2));
        List<StatePair> result = new ArrayList<>();
        for (Symbol s1 : q1Closure) {
            for (Symbol s2 : q2Closure) {
                result.add(new StatePair(s1, s2));
            }
        }
        return result;
    }

    private String setName(List<StatePair> state) {
        return state.stream()
                .sorted(Comparator.comparing(p -> p.q1 + "," + p.q2))
                .map(p -> "(" + p.q1 + "," + p.q2 + ")")
                .collect(Collectors.joining("|"));
    }

    private List<Symbol> epsClosureN1(List<Symbol> states) {
        Set<Symbol> closure = new HashSet<>();
        Stack<Symbol> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            Symbol current = stack.pop();
            closure.add(current);

            // Получаем все ε-переходы из current в N1
            for (DeltaQSigma delta : n1Delta) {
                if (delta.getLHSQ().equals(current) && delta.getLHSS().toString().equals("e")) {
                    Symbol nextState = delta.getRHSQ().get(0); // предполагаем, что переход ведет в одно состояние
                    if (!closure.contains(nextState)) {
                        stack.push(nextState);
                    }
                }
            }
        }
        return new ArrayList<>(closure);
    }

    private List<Symbol> epsClosureN2(List<Symbol> states) {
        Set<Symbol> closure = new HashSet<>();
        Stack<Symbol> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            Symbol current = stack.pop();
            closure.add(current);

            // Получаем все ε-переходы из current в N2
            for (DeltaQSigma delta : n2Delta) {
                if (delta.LHSQ.equals(current) && delta.LHSS.toString().equals("e")) {
                    Symbol nextState = delta.RHSQ.get(0);
                    if (!closure.contains(nextState)) {
                        stack.push(nextState);
                    }
                }
            }
        }
        return new ArrayList<>(closure);
    }

    private List<Symbol> moveN1(Symbol state, Symbol a) {
        List<Symbol> result = new ArrayList<>();
        for (DeltaQSigma delta : n1Delta) {
            if (delta.LHSQ.equals(state) && delta.LHSS.equals(a)) {
                result.addAll(delta.RHSQ); // добавляем все состояния, куда ведет переход
            }
        }
        return result;
    }

    private List<Symbol> moveN2(Symbol state, Symbol a) {
        List<Symbol> result = new ArrayList<>();
        for (DeltaQSigma delta : n2Delta) {
            if (delta.LHSQ.equals(state) && delta.LHSS.equals(a)) {
                result.addAll(delta.RHSQ); // добавляем все состояния, куда ведет переход
            }
        }
        return result;
    }

    private List<StatePair> parseState(Symbol state) {
        String stateStr = state.getSymbol();
        List<StatePair> pairs = new ArrayList<>();

        // Удаляем лишние скобки и разбиваем по "|"
        String[] pairStrings = stateStr.substring(1, stateStr.length() - 1).split("\\|");

        for (String pairStr : pairStrings) {
            String[] parts = pairStr.split(",");
            Symbol q1 = Symbol.stringToSymbol(parts[0].substring(1)); // "(q0" → "q0"
            Symbol q2 = Symbol.stringToSymbol(parts[1].substring(0, parts[1].length() - 1)); // "p0)" → "p0"
            pairs.add(new StatePair(q1, q2));
        }

        return pairs;
    }


}
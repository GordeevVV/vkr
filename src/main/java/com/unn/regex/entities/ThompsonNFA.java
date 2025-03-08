//package com.unn.regex.entities;
//
//import java.util.*;
//
//public class ThompsonNFA {
//
//    static class Fragment {
//        State start;
//        State end;
//        Fragment(State start, State end) {
//            this.start = start;
//            this.end = end;
//        }
//    }
//
//    private static int stateId = 0; // Счетчик для генерации ID состояний
//
//    public static NFA build(String regex) {
//        Stack<Fragment> stack = new Stack<>();
//        stateId = 0; // Сброс счетчика
//
//        for (int i = 0; i < regex.length(); i++) {
//            char c = regex.charAt(i);
//            switch (c) {
//                case '|':
//                    // Обработка альтернативы
//                    Fragment f2 = stack.pop();
//                    Fragment f1 = stack.pop();
//                    State split = new State(stateId++);
//                    split.addTransition('\0', f1.start);
//                    split.addTransition('\0', f2.start);
//                    State merge = new State(stateId++);
//                    f1.end.addTransition('\0', merge);
//                    f2.end.addTransition('\0', merge);
//                    stack.push(new Fragment(split, merge));
//                    break;
//                case '*':
//                    // Обработка итерации
//                    Fragment f = stack.pop();
//                    State loop = new State(stateId++);
//                    f.end.addTransition('\0', loop);
//                    loop.addTransition('\0', f.start);
//                    State start = new State(stateId++);
//                    start.addTransition('\0', f.start);
//                    start.addTransition('\0', loop);
//                    stack.push(new Fragment(start, loop));
//                    break;
//                case '+':
//                    // Обработка '+'
//                    Fragment fPlus = stack.pop();
//                    State loopPlus = new State(stateId++);
//                    fPlus.end.addTransition('\0', loopPlus);
//                    loopPlus.addTransition('\0', fPlus.start);
//                    stack.push(new Fragment(fPlus.start, loopPlus));
//                    break;
//                case '?':
//                    // Обработка '?'
//                    Fragment fOpt = stack.pop();
//                    State splitOpt = new State(stateId++);
//                    splitOpt.addTransition('\0', fOpt.start);
//                    splitOpt.addTransition('\0', fOpt.end);
//                    stack.push(new Fragment(splitOpt, fOpt.end));
//                    break;
//                default:
//                    // Обработка символа (конкатенация)
//                    State s1 = new State(stateId++);
//                    State s2 = new State(stateId++);
//                    s1.addTransition(c, s2);
//
//                    if (!stack.isEmpty()) {
//                        // Соединяем с предыдущим фрагментом
//                        Fragment prev = stack.pop();
//                        prev.end.addTransition('\0', s1);
//                        stack.push(new Fragment(prev.start, s2));
//                    } else {
//                        stack.push(new Fragment(s1, s2));
//                    }
//                    break;
//            }
//        }
//
//        Fragment finalFragment = stack.pop();
//        finalFragment.end.setAccept(true);
//        return new NFA(finalFragment.start, finalFragment.end);
//    }
//}

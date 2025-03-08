package com.unn.regex.entities;

import lombok.Data;
import lombok.Getter;

import java.util.*;

@Data
public class State {
    private boolean isAccept = false;
    private Integer id; // Идентификатор состояния
    private String regex; // Регулярное выражение, соответствующее состоянию
    private Map<Character, Set<State>> transitions = new HashMap<>();; // Переходы

    public State() {

    }

    public State(int i) {
        this.id = i;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    public Map<Character, Set<State>> getTransitions() {
        return transitions;
    }

    public Integer getId() {
        return id;
    }

    public String getRegex() {
        return regex;
    }

    public State(int id, String regex) {
        this.id = id;
        this.regex = regex;
    }

    public void addTransition(char symbol, State state) {
        transitions.computeIfAbsent(symbol, k -> new HashSet<>()).add(state);
    }

    public Set<State> getTransitions(char symbol) {
        return transitions.getOrDefault(symbol, new HashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return id == state.id && Objects.equals(regex, state.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regex);
    }

    @Override
    public String toString() {
        return "q" + id + "{" + regex + "}";
    }
}
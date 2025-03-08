package com.unn.regex.entities;

import java.util.*;

public class TompsonAutomata {
    public List<Symbol> Q = null; ///< множество состояний
    public List<Symbol> sigma = null; ///< множество алфавит
    public List<DeltaQSigma> delta = null;  ///< множество правил перехода
    public Symbol Q0 = null; ///< начальное состояние
    public List<Symbol> F = null; ///< множество конечных состояний
    private List<Symbol> config = new ArrayList<>();
    private List<DeltaQSigma> deltaD = new ArrayList<DeltaQSigma>(); ///< правила детерминированного автомата

    public TompsonAutomata() {
    }

    public TompsonAutomata(List<Symbol> Q, List<Symbol> Sigma, List<Symbol> F, Symbol q0) {
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


    private List<Symbol> epsClosure(List<Symbol> currStates) {
        return epsClosure(currStates, null);
    }

    private List<Symbol> epsClosure(List<Symbol> currStates, List<Symbol> ReachableStates) {
        if (ReachableStates == null) ReachableStates = new ArrayList<>();
        List<Symbol> nextStates = null;
        var next = new ArrayList<Symbol>();
        int count = currStates.size();
        for (int i = 0; i < count; i++) {

            nextStates = FromStateToStates(currStates.get(i).ToString(), "");

            if (!ReachableStates.contains(currStates.get(i))) {
                ReachableStates.add(new Symbol(currStates.get(i).ToString()));

            }
            if (nextStates != null) {

                for (var nxt : nextStates) {
                    ReachableStates.add(nxt);
                    next.add(nxt);
                }

            }
        }

        if (nextStates == null) return ReachableStates;
        else return epsClosure(next, ReachableStates);
    }

    private List<Symbol> move(List<Symbol> currStates, String term) {
        var ReachableStates = new ArrayList<Symbol>();
        var nextStates = new ArrayList<Symbol>();
        for (var s : currStates) {
            nextStates = (ArrayList<Symbol>) FromStateToStates(s.getSymbol(), term);
            if (nextStates != null)
                for (var st : nextStates) {
                    if (!ReachableStates.contains(st)) ReachableStates.add(st);
                }
        }
        return ReachableStates;
    }

    private List<Symbol> FromStateToStates(String currState, String term) {
        var NextStates = new ArrayList<Symbol>();
        boolean flag = false;
        for (var d : delta) {
            if (Objects.equals(d.LHSQ, Symbol.stringToSymbol(currState)) && Objects.equals(d.LHSS, Symbol.stringToSymbol(term))) {
                NextStates.add(new Symbol(d.RHSQ.get(0).ToString()));
                flag = true;
            }
        }
        if (flag) return NextStates;
        return null;
    }

    private void buildWithQueue(Symbol q0) {
        List<List<Symbol>> queue = new ArrayList<>(); // Имитируем очередь, ибо нельзя сделать queue<list<symbol>>
        List<Symbol> curStates;
        List<Symbol> newStates;
        boolean isStart = true;
        queue.add(List.of(q0));
        while (!queue.isEmpty()) {
            curStates = epsClosure(queue.get(0));
            queue.remove(0);
            for (var a : sigma) {
                newStates = move(curStates, a.getSymbol());
                if (!config.contains(Symbol.stringToSymbol(setName(epsClosure(newStates))))) {
                    if (isStart) {
                        config.add(Symbol.stringToSymbol(setName(curStates)));
                        isStart = false;
                    }
                    queue.add(newStates);
                    config.add(Symbol.stringToSymbol(setName(epsClosure(newStates))));
                }
                if (!newStates.isEmpty() && (!Objects.equals(setName(curStates), setName(epsClosure(newStates))))) {
                        DeltaQSigma deltaVar = new DeltaQSigma(Symbol.stringToSymbol(setName(curStates)),
                                Symbol.stringToSymbol(a.getSymbol()), List.of(Symbol.stringToSymbol(setName(epsClosure(newStates)))));
                        deltaD.add(deltaVar);
                }
            }
        }
    }

    public void BuildDeltaDKAutomate(FiniteStateAutomata ndka) {
        this.sigma = ndka.sigma;
        this.delta = ndka.delta;
        buildWithQueue(ndka.Q0);
        this.Q = config;
        this.Q0 = this.Q.get(0);
        this.delta = deltaD;
        this.F = getF(config, ndka.F);
    }

    private List<Symbol> getF(List<Symbol> config, List<Symbol> F) {
        var F_ = new ArrayList<Symbol>();
        for (var f : F) {
            for (var name : this.config) {
                if (name.getSymbol() != null && name.getSymbol().contains(f.getSymbol())) {
                    F_.add(name);
                }
            }
        }
        return F_;
    }

    private String setName(List<Symbol> list) {
        String line = null;
        if (list == null) {
            return "";
        }
        for (var sym : list) {
            line += sym.getSymbol();
        }

        return line;
    }


    public void Debug(String step, String line) {
        System.out.println(step + ": ");
        System.out.println(line);
    }

    public void Debug(String step, List<Symbol> list) {
        System.out.println(step + ": ");
        if (list == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) != null) System.out.println(list.get(i).ToString() + " ");
        System.out.println("\n");
    }

    public void Debug(List<Symbol> list) {
        System.out.println("{ ");
        if (list == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i).ToString() + " ");
        System.out.println(" }\n");
    }

    public void DebugAuto() {
        System.out.println("\nAutomate definition:");
        Debug("Q", this.Q);
        Debug("Sigma", this.sigma);
        Debug("Q0", this.Q0.getSymbol());
        Debug("F", this.F);
        System.out.println("DeltaList:");
        for (var d : this.delta) {
            d.toString();
        }
    }
}

package com.unn.regex.entities;

import java.util.List;
import java.util.Objects;

public class FiniteStateAutomata extends TompsonAutomata {
//    public FiniteStateAutomata(List<Symbol> Q, List<Symbol> Sigma, List<Symbol> F, String q0) {
//        super(Q, Sigma, F, Symbol.stringToSymbol(q0));
//    }

    public FiniteStateAutomata(List<String> Q, List<String> Sigma, List<String> F, String q0) {
        super(Q.stream().map(Symbol::new).toList(), Sigma.stream().map(Symbol::new).toList(),
                F.stream().map(Symbol::new).toList(), Symbol.stringToSymbol(q0));
    }

    public FiniteStateAutomata() {
        super();
    }

    public void Execute(String chineSymbol) {
        var currState = this.Q0;
        int flag = 0;
        int i = 0;
        for (; i < chineSymbol.length(); i++) {
            flag = 0;
                for (DeltaQSigma d : this.delta) {
                    if (d.LHSQ == currState && Objects.equals(d.LHSS, Symbol.stringToSymbol(chineSymbol.substring(i, 1))))
                    {
                        currState = Symbol.stringToSymbol(d.RHSQ.get(0).getSymbol()); // Для детерминированного К автомата
                        flag = 1;
                        break;
                    }
            }
            if (flag == 0) break;
        }

        System.out.println("Length: " + chineSymbol.length());
        System.out.println(" i :" + i);

        if (this.F.contains(currState) && i == chineSymbol.length())
            System.out.println("chineSymbol belongs to language");
        else
            System.out.println("chineSymbol doesn't belong to language");
    }


}

package com.unn.regex.entities;

import lombok.Data;

import java.util.List;

@Data
public class DeltaQSigma {
    public Symbol LHSQ = null; ///< Q  (q0)
    public Symbol LHSS = null; ///< Sigma (a)
    public List<Symbol> RHSQ = null; ///< Q  ([q1,q2...])

    public DeltaQSigma(Symbol LHSQ, Symbol LHSS, List<Symbol> RHSQ) {
        this.LHSQ = LHSQ;
        this.LHSS = LHSS;
        this.RHSQ = RHSQ;
    }


}

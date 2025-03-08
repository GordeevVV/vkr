package com.unn.regex.entities;

import lombok.Data;

import java.util.List;

@Data
public class DeltaQSigma {
    public Symbol LHSQ = null; ///< Q
    public Symbol LHSS = null; ///< Sigma
    public List<Symbol> RHSQ = null; ///< Q

    public DeltaQSigma(Symbol LHSQ, Symbol LHSS, List<Symbol> RHSQ) {
        this.LHSQ = LHSQ;
        this.LHSS = LHSS;
        this.RHSQ = RHSQ;
    }


}

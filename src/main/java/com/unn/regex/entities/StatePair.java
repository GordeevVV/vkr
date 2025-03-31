package com.unn.regex.entities;

import java.util.Objects;

class StatePair {
    Symbol q1;
    Symbol q2;

    StatePair(Symbol q1, Symbol q2) {
        this.q1 = q1;
        this.q2 = q2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatePair)) return false;
        StatePair that = (StatePair) o;
        return q1.equals(that.q1) && q2.equals(that.q2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(q1, q2);
    }
}


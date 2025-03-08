package com.unn.regex.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Symbol {
    private String symbol; ///< Строковое значение/имя символа
    private List<Symbol> attr = null; ///< Множество атрибутов символа

    private int production = 0;
    private int symbolPosition = 0;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<Symbol> getAttr() {
        return attr;
    }

    public void setAttr(List<Symbol> attr) {
        this.attr = attr;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getSymbolPosition() {
        return symbolPosition;
    }

    public void setSymbolPosition(int symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    public Symbol() {
    }

    public Symbol(String s, int production, int symbolPosition) {
        this.symbol = s;
        this.production = production;
        this.symbolPosition = symbolPosition;
    }

    public Symbol(String s, List<Symbol> attr) {
        this.symbol = s;
        attr = List.copyOf(attr);
        this.production = 0;
        this.symbolPosition = 0;
    }

    public Symbol(String value) {
        this.symbol = value;
        this.attr = null;
        this.production = 0;
        this.symbolPosition = 0;
    }

    /// Неявное преобразование строки в Symbol
    public static Symbol stringToSymbol(String str) {
        return new Symbol(str);
    }


    @Override
    public boolean equals(Object other) {
        return (other instanceof Symbol) && (this.symbol == ((Symbol) other).symbol);
    }


    public String ToString() {
        return this != Epsilon ? this.symbol : "e";
    }

    public static final Symbol Epsilon = new Symbol(""); ///< Пустой символ
    public static final Symbol Sentinel = new Symbol("$"); ///< Cимвол конца строки / Символ дна стека
}

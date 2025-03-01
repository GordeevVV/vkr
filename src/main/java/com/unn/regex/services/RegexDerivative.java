package com.unn.regex.services;

import org.springframework.stereotype.Service;

@Service
public class RegexDerivative {
    // Производная регулярного выражения по символу
    public String derivative(String regex, char symbol) {
        if (regex.isEmpty()) return "";
        if (regex.equals("ε")) return "";
        if (regex.length() == 1) {
            return regex.charAt(0) == symbol ? "ε" : "";
        }
        if (regex.startsWith("(") && regex.endsWith(")")) {
            return derivative(regex.substring(1, regex.length() - 1), symbol);
        }
        if (regex.contains("|")) {
            String[] parts = regex.split("\\|");
            return derivative(parts[0], symbol) + "|" + derivative(parts[1], symbol);
        }
        if (regex.contains("*")) {
            String base = regex.substring(0, regex.length() - 1);
            return derivative(base, symbol) + regex;
        }
        if (regex.contains("+")) {
            String base = regex.substring(0, regex.length() - 1);
            return derivative(base, symbol) + base + "*";
        }
        if (regex.contains("?")) {
            String base = regex.substring(0, regex.length() - 1);
            return derivative(base, symbol) + "|ε";
        }
        return regex.startsWith(String.valueOf(symbol)) ? regex.substring(1) : "";
    }

    // Проверка, содержит ли регулярное выражение пустую строку
    public boolean containsEpsilon(String regex) {
        if (regex.equals("ε")) return true;
        if (regex.isEmpty()) return false;
        if (regex.length() == 1) return false;
        if (regex.contains("|")) {
            String[] parts = regex.split("\\|");
            return containsEpsilon(parts[0]) || containsEpsilon(parts[1]);
        }
        if (regex.contains("*")) return true;
        if (regex.contains("+")) return containsEpsilon(regex.substring(0, regex.length() - 1));
        if (regex.contains("?")) return true;
        return false;
    }
}

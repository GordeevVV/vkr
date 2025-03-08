package com.unn.regex.utils;

import com.unn.regex.services.RegexDerivative;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegexUtils {
    private final RegexDerivative regexDerivative;

    public RegexUtils(RegexDerivative regexDerivative) {
        this.regexDerivative = regexDerivative;
    }

    // Проверка пустоты языка
    public boolean isLanguageEmpty(String regex) {
        char[] alphabet = {'a', 'b'}; // Алфавит
        for (char symbol : alphabet) {
            String derivative = regexDerivative.derivative(regex, symbol);
            if (!derivative.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // Проверка эквивалентности двух регулярных выражений
    public boolean areEquivalent(String regex1, String regex2) {
        char[] alphabet = {'a', 'b'}; // Алфавит
        for (char symbol : alphabet) {
            String derivative1 = regexDerivative.derivative(regex1, symbol);
            String derivative2 = regexDerivative.derivative(regex2, symbol);
            if (!derivative1.equals(derivative2)) {
                return false;
            }
        }
        return true;
    }

    // Упрощение регулярного выражения
    public static String simplify(String regex) {
        if (regex == null || regex.isEmpty()) {
            return regex;
        }

        // Базовые случаи
        if (regex.equals("∅") || regex.equals("ε")) {
            return regex;
        }

        // Упрощение для одиночных символов
        if (regex.length() == 1) {
            return regex;
        }

        // Упрощение для группировки
        if (regex.startsWith("(") && regex.endsWith(")")) {
            String inner = regex.substring(1, regex.length() - 1);
            String simplifiedInner = simplify(inner);
            if (!simplifiedInner.contains("|") && !simplifiedInner.contains("·")) {
                return simplifiedInner; // (a) → a
            }
            return "(" + simplifiedInner + ")";
        }

        // Упрощение для альтернатив
        if (regex.contains("|")) {
            String[] parts = regex.split("\\|");
            Set<String> uniqueParts = new HashSet<>();
            for (String part : parts) {
                String simplifiedPart = simplify(part);
                if (!simplifiedPart.equals("∅")) { // Игнорируем ∅ в альтернативах
                    uniqueParts.add(simplifiedPart);
                }
            }
            if (uniqueParts.isEmpty()) {
                return "∅"; // Все части были ∅
            }
            if (uniqueParts.size() == 1) {
                return uniqueParts.iterator().next();
            }
            return String.join("|", uniqueParts);
        }

        // Упрощение для конкатенаций
        if (regex.contains("·")) {
            String[] parts = regex.split("·");
            StringBuilder simplified = new StringBuilder();
            for (String part : parts) {
                String simplifiedPart = simplify(part);
                if (simplifiedPart.equals("∅")) {
                    return "∅"; // Если хотя бы одна часть ∅, вся конкатенация ∅
                }
                if (!simplifiedPart.equals("ε")) {
                    simplified.append(simplifiedPart);
                }
            }
            return simplified.length() == 0 ? "ε" : simplified.toString();
        }

        // Упрощение для итераций
        if (regex.endsWith("*")) {
            String base = regex.substring(0, regex.length() - 1);
            String simplifiedBase = simplify(base);
            if (simplifiedBase.equals("∅") || simplifiedBase.equals("ε")) {
                return "ε"; // ∅* = ε, ε* = ε
            }
            if (simplifiedBase.endsWith("*")) {
                return simplifiedBase; // (a*)* → a*
            }
            return simplifiedBase + "*";
        }



        return regex;
    }
}

package com.purang.formula;
import javax.script.*;
import java.util.*;

public class FormulaToCalculate {
    public static void main(final String[] args) {
        final String formula = "commissionRate*金额*day/360";
        final String params = "amount,700000,day,29,commissionRate,0.0004";
        final String convertToFormula = convertToFormula(formula, params);
        System.out.println(convertToFormula);
    }

    public static String convertToFormula(String formula, final String params) {
        final ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        final String[] split = params.split(",");
        final Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < split.length - 1; i += 2) {
            map.put(split[i], split[i + 1]);
        }
        for (final Map.Entry entry : map.entrySet()) {
            final String key = (String) entry.getKey();
            final String value = (String) entry.getValue();
            formula = formula.replaceAll(key, value);
        }
        Object eval = null;
        try {
            eval = jse.eval(formula);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.valueOf(eval);
    }
}

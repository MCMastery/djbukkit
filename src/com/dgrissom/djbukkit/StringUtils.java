package com.dgrissom.djbukkit;

import org.bukkit.ChatColor;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    private StringUtils() {}

    public static String join(String[] array, String separator, int start) {
        String string = "";
        for (int i = start; i < array.length; i++)
            string += array[i] + separator;
        // remove last separator
        if (string.endsWith(separator))
            string = string.substring(0, string.length() - separator.length());
        return string;
    }

    public static String removeExtraSpaces(String s) {
        return s.trim().replace("  ", " ");
    }

    // String... variables is array like this: [name, value, name, value, name, value etc.]
    public static String insertVariables(String string, String... variables) {
        for (int i = 0; i < variables.length - 1; i++) {
            String variableName = variables[i];
            String variableValue = variables[i + 1];
            string = string.replace("%" + variableName + "%", variableValue);
        }
        return string;
    }
    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String unformat(String s) {
        return ChatColor.stripColor(s);
    }

    public static String format(BigInteger number) {
        return new DecimalFormat("#,###,###").format(number);
    }



    // example: used in /help (multiple pages)
    public static List<String> getPage(int pageNumber, int maxLines, List<String> book) {
        List<String> page = new ArrayList<>();
        int start = maxLines * pageNumber;
        for (int i = start; i < start + maxLines && i < book.size(); i++)
            page.add(book.get(i));
        return page;
    }
    public static int getPageCount(int maxLines, List<String> book) {
        return (int) Math.ceil(book.size() / (double) maxLines);
    }
}

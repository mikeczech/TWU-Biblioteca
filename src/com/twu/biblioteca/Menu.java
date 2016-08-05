package com.twu.biblioteca;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by mczech on 05/08/16.
 */
public class Menu {

    private Map<Character, String> options = new HashMap<>();
    private char nextOptionKey = 'a';

    public void addOption(String option) {
        options.put(nextOptionKey, option);
        nextOptionKey++;
    }

    public void show() {
        for(Character optionKey : options.keySet())
            System.out.println(optionKey + ") " + options.get(optionKey));
    }

    public void readInput() {
        System.out.println("Hello World");
    }

    public void addOption(String key, Action action) {
    }
}

package com.twu.biblioteca;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private final Map<Character, String> optionIdToLabel = new HashMap<>();
    private final Map<Character, Action> optionIdToAction = new HashMap<>();
    private char nextOptionKey = 'a';

    void addOptionWithLabel(String option) {
        optionIdToLabel.put(nextOptionKey, option);
        nextOptionKey++;
    }

    public void applyOptionWithId(char optionId) throws IOException {
        if(!optionIdToLabel.containsKey(optionId))
            throw new IOException("Unknown option key.");
        optionIdToAction.get(optionId).apply();
    }

    public Menu addOptionWithLabelAndAction(String optionLabel, Action action) {
        optionIdToAction.put(nextOptionKey, action);
        addOptionWithLabel(optionLabel);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Character optionId : optionIdToLabel.keySet())
            builder.append(optionId).append(") ").append(optionIdToLabel.get(optionId)).append("\n");
        return builder.toString();
    }
}

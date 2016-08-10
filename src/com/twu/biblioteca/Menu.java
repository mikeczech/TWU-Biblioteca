package com.twu.biblioteca;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Menu {

    private final Map<Character, String> optionIdToLabel = new HashMap<>();
    private final Map<Character, Action> optionIdToAction = new HashMap<>();
    private char nextOptionId = 'a';

    private final Set<Character> optionIdsThatRequireAuth = new HashSet<>();

    void addOptionWithLabel(String optionLabel) {
        optionIdToLabel.put(nextOptionId, optionLabel);
        nextOptionId++;
    }

    public void applyOptionWithId(char optionId) throws IOException {
        if(!optionIdToLabel.containsKey(optionId))
            throw new IOException("Unknown option key.");
        optionIdToAction.get(optionId).apply();
    }

    public Menu addOption(String optionLabel, Action action) {
        optionIdToAction.put(nextOptionId, action);
        addOptionWithLabel(optionLabel);
        return this;
    }

    public Menu addOptionThatRequiresAuthentication(String optionLabel, Action action) {
        optionIdsThatRequireAuth.add(nextOptionId);
        addOption(optionLabel, action);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Character optionId : optionIdToLabel.keySet())
            if(!optionIdsThatRequireAuth.contains(optionId) || UserSession.isLoggedIn())
                builder.append(optionId).append(") ").append(optionIdToLabel.get(optionId)).append("\n");
        return builder.toString();
    }
}

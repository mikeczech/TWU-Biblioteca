package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by mczech on 05/08/16.
 */
public class Menu {

    private List<String> options = new ArrayList<>();

    public void addOption(String option) {
        options.add(option);
    }

    public void show() {
        IntStream.range(0, options.size()).forEach(i ->
            System.out.println(((char)(i+'a')) + ") " + options.get(i))
        );
    }

}

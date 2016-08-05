package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mczech on 05/08/16.
 */
public class Menu {

    private List<String> options = new ArrayList<>();

    public void addOption(String option) {
        options.add(option);
    }

    public void show() {
        if(options.size() == 1)
            System.out.println("a) List Options");
        else {
            System.out.println("a) List Options");
            System.out.println("b) Quit");
        }
    }

}

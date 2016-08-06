package com.twu.biblioteca;


import java.io.IOException;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private Menu menu = new Menu();

    public BibliotecaApp() {
        buildMenu();
    }

    public void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    private void buildMenu() {
        menu.addOption("List Books");
        menu.addOption("Quit");
    }

    public void startAndThenQuit() {
        try {
            showWelcomeMessage();
            System.out.println();
            menu.show();
            menu.processInput();
        } catch(IOException ex) {
            System.out.println();
            System.out.println("Select a valid option!");
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

}

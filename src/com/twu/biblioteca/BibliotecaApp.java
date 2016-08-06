package com.twu.biblioteca;


import java.io.IOException;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private Menu menu = new Menu();

    private Library lib;

    public BibliotecaApp() {
        buildMenu();
    }

    public BibliotecaApp(Book ...books) {
        this();
        this.lib = new Library(books);
    }

    public void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    private void buildMenu() {
        menu.addOption("List Books", () -> lib.listBooks());
        menu.addOption("Quit");
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

    public void start(int inputLoopUpperBound) {
            showWelcomeMessage();
            System.out.println();
            menu.show();
            int i = 0;
            while(i < inputLoopUpperBound) {
                System.out.println();
                tryProcessInput();
                i++;
            }
    }

    private void tryProcessInput() {
        try {
            menu.processInput();
        } catch(IOException ex) {
            System.out.println("Select a valid option!");
        }
    }
}

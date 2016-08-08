package com.twu.biblioteca;


import java.io.IOException;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private Menu menu = new Menu();

    private Library lib;

    private boolean stop = false;

    public BibliotecaApp() {
        buildMenu();
    }

    private void buildMenu() {
        menu.addOption("List Books", () -> lib.listBooks());
        menu.addOption("Check-out Book", () -> System.out.print("checkout"));
        menu.addOption("Return Book", () -> System.out.print("return"));
        menu.addOption("Quit", () -> stop = true);
    }

    public BibliotecaApp(Book ...books) {
        this();
        this.lib = new Library(books);
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

    public void start(int inputLoopUpperBound) {
            showWelcomeMessage();
            showMainMenu();
            int i = 0;
            while(i < inputLoopUpperBound && !stop) {
                readAndProcessInput();
                i++;
            }
    }

    protected void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    protected void showMainMenu() {
        System.out.println();
        menu.show();
    }

    protected void readAndProcessInput() {
        System.out.println();
        tryProcessInput();
    }

    private void tryProcessInput() {
        try {
            menu.processInput();
        } catch(IOException ex) {
            System.out.println("Select a valid option!");
        }
    }
}

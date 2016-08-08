package com.twu.biblioteca;


import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.IOException;
import java.util.Scanner;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private Menu menu;

    private Library lib;

    private boolean stop = false;

    private Scanner scanner;

    protected BibliotecaApp(Scanner scanner) {
        this.scanner = scanner;
        buildMenu();
    }

    private void buildMenu() {
        menu = new Menu(scanner);
        menu.addOption("List Books", () -> lib.listBooks());
        menu.addOption("Check-out Book", () -> tryToCheckoutBook());
        menu.addOption("Return Book", () -> {
            int input = requestBookId();
            System.out.println("Thank you for returning the book.");
            lib.returnBookWithGivenId(input - 1);
        });
        menu.addOption("Quit", () -> stop = true);
    }

    private int requestBookId() {
        System.out.print("Book ID: ");
        if(!scanner.hasNextLine())
            throw new IllegalStateException("No input was given.");
        return Integer.parseInt(scanner.nextLine());
    }

    private void tryToCheckoutBook() {
        try {
            int input = requestBookId();
            lib.checkoutBookWithId(input - 1);
            System.out.println("Thank you! Enjoy the book");
        } catch(IllegalStateException | IllegalArgumentException ex) {
            System.out.println();
            System.out.println("That book is not available.");
        }
    }

    public BibliotecaApp() {
        this(new Scanner(""));
    }

    public BibliotecaApp(Scanner scanner, Book ...books) {
        this(scanner);
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

package com.twu.biblioteca;

import java.io.IOException;
import java.time.Year;
import java.util.Scanner;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";
    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!";
    private static final String SUCCESSFUL_RETURN_MESSAGE = "Thank you for returning the book.";
    private static final String UNSUCCESSFUL_RETURN_MESSAGE = "That is not a valid book to return.";
    private static final String SUCCESSFUL_CHECKOUT_MESSAGE = "Thank you! Enjoy the book";
    private static final String UNSUCCESSFUL_CHECKOUT_MESSAGE = "That book is not available.";

    private Menu menu;

    private Library lib;

    private boolean stop = false;

    private Scanner inputScanner;

    protected BibliotecaApp(Scanner inputScanner) {
        this.inputScanner = inputScanner;
        buildMenu();
    }

    private void buildMenu() {
        menu = new Menu(inputScanner);
        menu.addOption("List Books", () -> showLibrary());
        menu.addOption("Check-out Book", () -> tryToCheckoutBook());
        menu.addOption("Return Book", () -> tryToReturnBook());
        menu.addOption("Quit", () -> stop = true);
    }

    private void showLibrary() {
        System.out.print(lib);
    }

    private void tryToReturnBook() {
        try {
            int input = requestBookId();
            lib.returnBookWithGivenId(input - 1);
            System.out.println(SUCCESSFUL_RETURN_MESSAGE);
        } catch(IllegalStateException | IllegalArgumentException ex) {
            System.out.println();
            System.out.println(UNSUCCESSFUL_RETURN_MESSAGE);
        }
    }

    private int requestBookId() {
        System.out.print("Book ID: ");
        if(!inputScanner.hasNextLine())
            throw new IllegalStateException("No input was given.");
        return Integer.parseInt(inputScanner.nextLine());
    }

    private void tryToCheckoutBook() {
        try {
            int input = requestBookId();
            lib.checkoutBookWithId(input - 1);
            System.out.println(SUCCESSFUL_CHECKOUT_MESSAGE);
        } catch(IllegalStateException | IllegalArgumentException ex) {
            System.out.println();
            System.out.println(UNSUCCESSFUL_CHECKOUT_MESSAGE);
        }
    }

    protected BibliotecaApp() {
        this(new Scanner(""));
    }

    public BibliotecaApp(Scanner inputScanner, Book ...books) {
        this(inputScanner);
        this.lib = new Library(books);
    }

    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner(System.in),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)),
                new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", Year.of(1979))
        );
        app.start();
    }

    protected void start(int inputLoopUpperBound) {
            showWelcomeMessage();
            showMainMenu();
            int i = 0;
            while((i < inputLoopUpperBound && !stop)) {
                readAndProcessInput();
                i++;
            }
    }

    public void start() {
        start(Integer.MAX_VALUE);
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
            System.out.println(INVALID_OPTION_MESSAGE);
        }
    }
}

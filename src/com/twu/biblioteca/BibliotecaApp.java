package com.twu.biblioteca;

import java.io.IOException;
import java.time.Year;
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
        menu.addOption("Return Book", () -> tryToReturnBook());
        menu.addOption("Quit", () -> stop = true);
    }

    private void tryToReturnBook() {
        try {
            int input = requestBookId();
            lib.returnBookWithGivenId(input - 1);
            System.out.println("Thank you for returning the book.");
        } catch(IllegalStateException | IllegalArgumentException ex) {
            System.out.println();
            System.out.println("That is not a valid book to return.");
        }
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

    protected BibliotecaApp() {
        this(new Scanner(""));
    }

    public BibliotecaApp(Scanner scanner, Book ...books) {
        this(scanner);
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
            System.out.println("Select a valid option!");
        }
    }
}

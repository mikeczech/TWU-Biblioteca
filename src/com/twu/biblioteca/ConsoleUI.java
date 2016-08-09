package com.twu.biblioteca;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


class ConsoleUI {

    private final Library<Book> bookLibrary;
    private final Library<Movie> movieLibrary;

    private final Menu menu;

    private final Scanner scanner;

    private boolean stopInputLoop = false;

    ConsoleUI(InputStream input, Library<Book> bookLibrary, Library<Movie> movieLibrary) {
        this.bookLibrary = bookLibrary;
        this.movieLibrary = movieLibrary;
        this.scanner = new Scanner(input);
        this.menu = new Menu();
        buildMenu();
    }

    public ConsoleUI(Library<Book> bookLibrary, Library<Movie> movieLibrary) {
        this(System.in, bookLibrary, movieLibrary);
    }

    private void buildMenu() {
        menu.addOptionWithLabelAndAction(Message.LIST_BOOKS, this::listBooks)
        .addOptionWithLabelAndAction(Message.CHECK_OUT_BOOK, this::tryToCheckoutBook)
        .addOptionWithLabelAndAction(Message.RETURN_BOOK, this::tryToReturnBook)
        .addOptionWithLabelAndAction("List Movies", this::listMovies)
        .addOptionWithLabelAndAction("Check-out Movie", this::tryToCheckoutMovie)
        .addOptionWithLabelAndAction("Return Movie", this::tryToReturnMovie)
        .addOptionWithLabelAndAction(Message.QUIT, this::quit);
    }

    private void tryToReturnMovie() {
    }

    private void tryToCheckoutMovie() {
    }

    private void listMovies() {
        System.out.print(movieLibrary.toString());
    }

    private void listBooks() {
        System.out.print(bookLibrary.toString());
    }

    private void tryToCheckoutBook() {
        try {
            int input = readBookId();
            bookLibrary.checkoutItemWithId(input - 1);
            showSuccessfulCheckoutMessage();
        } catch(IllegalStateException | IllegalArgumentException ex) {
            showUnsuccessfulCheckoutMessage();
        }
    }

    private void showSuccessfulCheckoutMessage() {
        System.out.println(Message.SUCCESSFUL_CHECKOUT);
    }

    private void showUnsuccessfulCheckoutMessage() {
        System.out.println();
        System.out.println(Message.UNSUCCESSFUL_CHECKOUT);
    }

    private void tryToReturnBook() {
        try {
            int input = readBookId();
            bookLibrary.returnItemWithId(input - 1);
            showSuccessfulReturnMessage();
        } catch(IllegalStateException | IllegalArgumentException ex) {
            showUnsuccessfulReturnMessage();
        }
    }

    private void showSuccessfulReturnMessage() {
        System.out.println(Message.SUCCESSFUL_RETURN);
    }

    private void showUnsuccessfulReturnMessage() {
        System.out.println();
        System.out.println(Message.UNSUCCESSFUL_RETURN);
    }

    private int readBookId() {
        showPleaseSelectBookIdMessage();
        if(!scanner.hasNextLine())
            throw new IllegalStateException("No input was given.");
        return Integer.parseInt(scanner.nextLine());
    }

    private void showPleaseSelectBookIdMessage() {
        System.out.print(Message.SELECT_BOOKID);
    }

    public void show() {
        showWelcomeMessage();
        showMainMenu();
        while(!stopInputLoop) {
            readAndProcessInput();
        }
    }

    private void showWelcomeMessage() {
        System.out.println(Message.WELCOME);
    }

    private void showMainMenu() {
        System.out.println();
        System.out.print(menu);
    }

    private void readAndProcessInput() {
        System.out.println();
        trySelectMenuOption();
    }

    private void trySelectMenuOption() {
        try {
            char optionId = readMenuOption();
            applyMenuOption(optionId);
        } catch(IOException ex) {
            System.out.println(Message.INVALID_OPTION);
        }
    }

    private char readMenuOption() throws IOException {
        showPleaseSelectOptionMessage();
        if(!scanner.hasNextLine())
            throw new IOException("Empty input.");
        String input = scanner.nextLine();
        if(input.length() != 1)
            throw new IOException("Input must consist of exactly one character.");
        return input.charAt(0);
    }

    private void showPleaseSelectOptionMessage() {
        System.out.print("Please select an option: ");
    }

    private void applyMenuOption(char optionId) throws IOException {
        menu.applyOptionWithId(optionId);
    }

    private void quit() {
        stopInputLoop = true;
    }

}

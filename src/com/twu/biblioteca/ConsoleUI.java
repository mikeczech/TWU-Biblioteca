package com.twu.biblioteca;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class ConsoleUI {

    private Library library;

    private Menu menu;

    private Scanner scanner;

    private boolean stopInputLoop = false;

    protected ConsoleUI(InputStream input, Library library) {
        this.library = library;
        this.scanner = new Scanner(input);
        this.menu = new Menu();
        buildMenu();
    }

    public ConsoleUI(Library library) {
        this(System.in, library);
    }

    private void buildMenu() {
        menu.addOptionWithLabelAndAction(Message.LIST_BOOKS, () -> showLibrary())
        .addOptionWithLabelAndAction(Message.CHECK_OUT_BOOK, () -> tryToCheckoutBook())
        .addOptionWithLabelAndAction(Message.RETURN_BOOK, () -> tryToReturnBook())
        .addOptionWithLabelAndAction(Message.QUIT, () -> quit());
    }

    private void showLibrary() {
        System.out.print(library.toString());
    }

    private void tryToCheckoutBook() {
        try {
            int input = readBookId();
            library.checkoutBookWithId(input - 1);
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
            library.returnBookWithGivenId(input - 1);
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

    protected void showWelcomeMessage() {
        System.out.println(Message.WELCOME);
    }

    protected void showMainMenu() {
        System.out.println();
        System.out.print(menu);
    }

    protected void readAndProcessInput() {
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

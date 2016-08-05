package com.twu.biblioteca;

import java.time.Year;
import java.util.Arrays;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private Book library;

    public BibliotecaApp(Book book) {
        this.library = book;
    }

    public BibliotecaApp() {
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

    public void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void listBooks() {
        if(library != null)
            System.out.println("(1) " + library +  " [AVAILABLE]");
    }

}

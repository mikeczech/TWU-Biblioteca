package com.twu.biblioteca;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BibliotecaApp {

    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!";

    private List<Book> library = new ArrayList<>();

    public BibliotecaApp(Book ...books) {
        for(Book b : books)
            library.add(b);
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }

    public void showWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void listBooks() {
        IntStream.range(0, library.size()).forEach(i ->
            System.out.println("(" + (i+1) + ") " + library.get(i) +  " [AVAILABLE]")
        );
    }

}

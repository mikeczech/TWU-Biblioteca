package com.twu.biblioteca;

import java.time.Year;

class BibliotecaApp {

    private final ConsoleUI ui;

    private SecureLibrary books;
    private Library movies;

    private BibliotecaApp() {
        fillLibraries();
        ui = new ConsoleUI(books, movies);
    }

    private void fillLibraries() {
        fillBookLibrary();
        fillMovieLibrary();
    }

    private void fillBookLibrary() {
        books = new SecureLibrary<>(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)),
                new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", Year.of(1979))
        );
    }

    private void fillMovieLibrary() {
        movies = new Library<>(
                new Movie("E.T. the Extra-Terrestrial", "Steven Spielberg", Year.of(1982), Rating.UNRATED),
                new Movie("The Hobbit", "Peter Jackson", Year.of(2012), Rating.of(3)),
                new Movie("The Terminator", "James Cameron", Year.of(1984), Rating.of(9))
        );
    }

    private void start() {
        ui.show();
    }

    public static void main(String[] args) {
        BibliotecaApp app = new BibliotecaApp();
        app.start();
    }

}

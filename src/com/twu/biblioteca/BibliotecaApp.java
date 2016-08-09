package com.twu.biblioteca;

import java.time.Year;

class BibliotecaApp {

    private final ConsoleUI ui;

    private BibliotecaApp(Library<Book> bookLibrary, Library<Movie> movieLibrary) {
        ui = new ConsoleUI(bookLibrary, movieLibrary);
    }

    private void start() {
        ui.show();
    }

    public static void main(String[] args) {
        Library bookLibrary = new Library(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)),
                new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", Year.of(1979))
        );
        Library movieLibrary = new Library(
                new Movie("E.T. the Extra-Terrestrial", "Steven Spielberg", Year.of(1982), Rating.UNRATED),
                new Movie("The Hobbit", "Peter Jackson", Year.of(2012), Rating.of(3)),
                new Movie("The Terminator", "James Cameron", Year.of(1984), Rating.of(9))
        );
        BibliotecaApp app = new BibliotecaApp(bookLibrary, movieLibrary);
        app.start();
    }

}

package com.twu.biblioteca;

import java.time.Year;

class BibliotecaApp {

    private final ConsoleUI ui;

    private BibliotecaApp(Library library) {
        ui = new ConsoleUI(library);
    }

    private void start() {
        ui.show();
    }

    public static void main(String[] args) {
        Library library = new Library(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)),
                new Book("The Hitchhiker's Guide to the Galaxy", "Douglas Adams", Year.of(1979))
        );
        BibliotecaApp app = new BibliotecaApp(library);
        app.start();
    }

}

package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void showWelcomeMessage() {
        BibliotecaApp app = new BibliotecaApp();
        app.showWelcomeMessage();
        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    public void welcomeMessageIsSpecificText() {
        BibliotecaApp app = new BibliotecaApp();
        app.showWelcomeMessage();
        assertEquals("Welcome to Biblioteca!\n", outContent.toString());
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        BibliotecaApp app = new BibliotecaApp();

        app.start(0);

        assertEquals("Welcome to Biblioteca!\n\na) List Books\nb) Quit\n", outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidOptionAMessageIsShown() {
        BibliotecaApp app = new BibliotecaApp();
        ByteArrayInputStream in = new ByteArrayInputStream("c".getBytes());
        System.setIn(in);

        app.start(1);

        assertEquals("Welcome to Biblioteca!\n\na) List Books\nb) Quit\n\n" +
                "Select a valid option!\n", outContent.toString());
    }

    @Test
    public void afterSelectingAnInvalidOptionWeCanChooseAnotherOne() {
        BibliotecaApp app = new BibliotecaApp();
        ByteArrayInputStream in = new ByteArrayInputStream("c\nc".getBytes());
        System.setIn(in);

        app.start(2);

        assertEquals("Welcome to Biblioteca!\n\na) List Books\nb) Quit\n\n" +
                "Select a valid option!\n\n" +
                "Select a valid option!\n", outContent.toString());
    }

    @Test
    public void whenSelectingListBooksAListOfBooksIsShown() {
        BibliotecaApp app = new BibliotecaApp(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945))
        );
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        app.start(1);

        assertEquals("Welcome to Biblioteca!\n\na) List Books\nb) Quit\n\n" +
                "(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n" +
                "(2) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

}
package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    private static final String MAIN_MENU = "a) List Books\nb) Check-out Book\nc) Return Book\nd) Quit\n";
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca!\n\n";
    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        BibliotecaApp app = new BibliotecaApp();

        app.start(0);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU, outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidOptionAMessageIsShown() {
        BibliotecaApp app = new BibliotecaApp(new Scanner("e\n"));

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" +
                INVALID_OPTION_MESSAGE, outContent.toString());
    }

    @Test
    public void afterSelectingAnInvalidOptionWeCanChooseAnotherOne() {
        BibliotecaApp app = new BibliotecaApp(new Scanner("e\ne\n"));

        app.start(2);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" +
                INVALID_OPTION_MESSAGE + "\n" +
                INVALID_OPTION_MESSAGE, outContent.toString());
    }

    @Test
    public void whenSelectingListBooksAListOfBooksIsShown() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("a\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" +
                "(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n" +
                "(2) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void whenSelectingQuitNoFurtherOutputIsShown() {
        BibliotecaApp app = new BibliotecaApp(new Scanner("d\n"));

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n", outContent.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenSelectingCheckoutBookTheUserIsAskedtoProvideABookID() {
        BibliotecaApp app = new BibliotecaApp(new Scanner("b\n"));

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: ", outContent.toString());
    }

    @Test
    public void whenSelectingCheckoutBookAndProvidingABookIDTheSelectedBookShouldAppearAsCheckedOut() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n1\na\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "\n" +
                "(1) Brave New World, Aldous Huxley, 1932 [NOT AVAILABLE]\n" +
                "(2) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

}
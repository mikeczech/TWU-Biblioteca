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
    private static final String INVALID_BOOK_MESSAGE = "That book is not available.\n";

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
                "(1) Brave New World, Aldous Huxley, 1932\n" +
                "(2) Animal Farm, George Orwell, 1945\n", outContent.toString());
    }

    @Test
    public void whenSelectingQuitNoFurtherOutputIsShown() {
        BibliotecaApp app = new BibliotecaApp(new Scanner("d\n"));

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n", outContent.toString());
    }

    @Test
    public void whenSelectingCheckoutBookTheUserIsAskedtoProvideABookID() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n1"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you! Enjoy the book\n", outContent.toString());
    }

    @Test
    public void whenProvidingTheFirstBookIDTheSelectedBookShouldAppearAsCheckedOut() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n1\na\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(2);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you! Enjoy the book\n\n" , outContent.toString());
    }

    @Test
    public void whenProvidingTheSecondBookIDTheSecondBookShouldAppearAsCheckedOut() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n2\na\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945))
        );

        app.start(2);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you! Enjoy the book\n\n" +
                "(1) Brave New World, Aldous Huxley, 1932\n", outContent.toString());
    }

    @Test
    public void whenProvidingTheIDOfAnAlreadyCheckedOutBookAMessageAppears() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n1\nb\n1\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(2);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you! Enjoy the book\n\n"
                + "Book ID: \n" + INVALID_BOOK_MESSAGE, outContent.toString());
    }

    @Test
    public void whenProvidingNoBookIdAMessageAppears() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "\n" +
                INVALID_BOOK_MESSAGE, outContent.toString());
    }

    @Test
    public void whenProvidingAnInvalidBookIdAMessageAppears() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\nFOO\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "\n" +
                INVALID_BOOK_MESSAGE, outContent.toString());
    }

    @Test
    public void whenSelectingReturnBookTheUserIsAskedtoProvideABookID() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("c\n1"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(1);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you for returning the book.\n", outContent.toString());
    }

    @Test
    public void whenTheFirstBookIsReturnedTheBookShouldAppearAsAvailableAgain() {
        BibliotecaApp app = new BibliotecaApp(
                new Scanner("b\n1\nc\n1\na\n"),
                new Book("Brave New World", "Aldous Huxley", Year.of(1932))
        );

        app.start(3);

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n" + "Book ID: " + "Thank you! Enjoy the book\n\n" +
                "Book ID: " + "Thank you for returning the book.\n" +
                "(1) Brave New World, Aldous Huxley, 1932\n", outContent.toString());
    }
}
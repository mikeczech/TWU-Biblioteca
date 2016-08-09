package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

public class ConsoleUITest {

    private static final String MAIN_MENU = "a) List Books\nb) Check-out Book\nc) Return Book\nd) Quit\n";
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca!\n\n";
    private static final String INITIAL_OUTPUT = WELCOME_MESSAGE + MAIN_MENU + "\n";
    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";
    private static final String INVALID_BOOK_MESSAGE = "That book is not available.\n";
    private static final String BOOK1_ENTRY = "(1) Brave New World, Aldous Huxley, 1932\n";
    private static final String BOOK2_ENTRY = "(2) Animal Farm, George Orwell, 1945\n";
    private static final String COMPLETE_BOOKLIST = BOOK1_ENTRY + BOOK2_ENTRY;
    private static final String SELECT_BOOKID_MSG = "Book ID: ";
    private static final String THANK_YOU_MSG = "Thank you! Enjoy the book\n";
    private static final String NO_VALID_BOOK_TO_RETURN_MSG = "That is not a valid book to return.\n";
    private static final String VALID_RETURN_MSG = "Thank you for returning the book.\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    private ConsoleUI createUIWithInput(String input) {
        Library library = new Library(
            new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
            new Book("Animal Farm", "George Orwell", Year.of(1945))
        );
        return new ConsoleUI(new ByteArrayInputStream((input + "d\n").getBytes()), library);
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        ConsoleUI ui = createUIWithInput("");

        ui.show();

        assertEquals(WELCOME_MESSAGE + MAIN_MENU + "\n", outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidOptionAMessageIsShown() {
        ConsoleUI ui = createUIWithInput("e\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + INVALID_OPTION_MESSAGE + "\n", outContent.toString());
    }

    @Test
    public void afterSelectingAnInvalidOptionWeCanChooseAnotherOne() {
        ConsoleUI ui = createUIWithInput("e\ne\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT +
                INVALID_OPTION_MESSAGE + "\n" +
                INVALID_OPTION_MESSAGE + "\n", outContent.toString());
    }

    @Test
    public void whenSelectingListBooksAListOfBooksIsShown() {
        ConsoleUI ui = createUIWithInput("a\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + COMPLETE_BOOKLIST + "\n", outContent.toString());
    }

    @Test
    public void whenSelectingCheckoutBookTheUserIsAskedtoProvideABookID() {
        ConsoleUI ui = createUIWithInput("b\n1\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n", outContent.toString());
    }

    @Test
    public void whenCheckingOutTheSecondBookItShouldNotAppearInTheBooklist() {
        ConsoleUI ui = createUIWithInput("b\n2\na\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                BOOK1_ENTRY + "\n", outContent.toString());
    }

    @Test
    public void whenProvidingTheIDOfAnAlreadyCheckedOutBookAMessageAppears() {
        ConsoleUI ui = createUIWithInput("b\n1\nb\n1\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n"
                + SELECT_BOOKID_MSG + "\n" + INVALID_BOOK_MESSAGE + "\n", outContent.toString());
    }

    @Test
    public void whenProvidingAnInvalidBookIdAMessageAppears() {
        ConsoleUI ui = createUIWithInput("b\nFOO\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + "\n" +
                INVALID_BOOK_MESSAGE + "\n", outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidBookToReturnAMessageShouldAppear() {
        ConsoleUI ui = createUIWithInput("c\n1\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + "\n" +
                NO_VALID_BOOK_TO_RETURN_MSG + "\n", outContent.toString());
    }

    @Test
    public void whenTheFirstBookIsReturnedTheBookShouldAppearAgainInTheList() {
        ConsoleUI ui = createUIWithInput("b\n1\nc\n1\na\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                SELECT_BOOKID_MSG + VALID_RETURN_MSG + "\n" +
                COMPLETE_BOOKLIST + "\n", outContent.toString());
    }

    @Test
    public void whenTheSecondBookIsReturnedTheBookShouldAppearAgainInTheList() {
        ConsoleUI ui = createUIWithInput("b\n2\nc\n2\na\n");

        ui.show();

        assertEquals(INITIAL_OUTPUT + SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                SELECT_BOOKID_MSG + VALID_RETURN_MSG + "\n" +
                COMPLETE_BOOKLIST + "\n", outContent.toString());
    }
}
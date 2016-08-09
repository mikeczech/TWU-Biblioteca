package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

public class ConsoleUITest {

    private static final String SELECT_OPTION_MSG = "Please select an option: ";
    private static final String END_OF_OUTPUT = "\n" + SELECT_OPTION_MSG;
    private static final String MAIN_MENU = "a) List Books\nb) Check-out Book\nc) Return Book\nd) List Movies\ne) Check-out Movie\nf) Return Movie\ng) Quit\n";
    private static final String WELCOME_MESSAGE = "Welcome to Biblioteca!\n\n";
    private static final String INITIAL_OUTPUT = WELCOME_MESSAGE + MAIN_MENU + "\n" + SELECT_OPTION_MSG;
    private static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";
    private static final String INVALID_BOOK_MESSAGE = "That book is not available.\n";
    private static final String BOOK1_ENTRY = "(1) Brave New World, Aldous Huxley, 1932\n";
    private static final String BOOK2_ENTRY = "(2) Animal Farm, George Orwell, 1945\n";
    private static final String COMPLETE_BOOKLIST = BOOK1_ENTRY + BOOK2_ENTRY;
    private static final String COMPLETE_MOVIELIST = "(1) E.T. the Extra-Terrestrial, Steven Spielberg, 1982, UNRATED\n(2) The Terminator, James Cameron, 1984, 9\n";
    private static final String SELECT_BOOKID_MSG = "Please select a book ID: ";
    private static final String THANK_YOU_MSG = "Thank you! Enjoy the book\n";
    private static final String NO_VALID_BOOK_TO_RETURN_MSG = "That is not a valid book to return.\n";
    private static final String VALID_RETURN_MSG = "Thank you for returning the book.\n";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    private ConsoleUI createUIWithInput(String input) {
        Library bookLibrary = new Library<>(
            new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
            new Book("Animal Farm", "George Orwell", Year.of(1945))
        );
        Library movieLibrary = new Library<>(
            new Movie("E.T. the Extra-Terrestrial", "Steven Spielberg", Year.of(1982), Rating.UNRATED),
            new Movie("The Terminator", "James Cameron", Year.of(1984), Rating.of(9))
        );
        return new ConsoleUI(new ByteArrayInputStream((input + "g\n").getBytes()), bookLibrary, movieLibrary);
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        ConsoleUI ui = createUIWithInput("");
        ui.show();
        assertEquals(WELCOME_MESSAGE + MAIN_MENU + END_OF_OUTPUT, outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidOptionAMessageIsShown() {
        ConsoleUI ui = createUIWithInput("k\n");
        ui.show();
        assertOutputIs(INVALID_OPTION_MESSAGE);
    }

    private void assertOutputIs(String expected) {
        assertEquals(INITIAL_OUTPUT + expected + END_OF_OUTPUT, outContent.toString());
    }

    @Test
    public void afterSelectingAnInvalidOptionWeCanChooseAnotherOne() {
        ConsoleUI ui = createUIWithInput("k\nk\n");
        ui.show();
        assertOutputIs(INVALID_OPTION_MESSAGE + "\n" + SELECT_OPTION_MSG + INVALID_OPTION_MESSAGE);
    }

    @Test
    public void whenSelectingListBooksAListOfBooksIsShown() {
        ConsoleUI ui = createUIWithInput("a\n");
        ui.show();
        assertOutputIs(COMPLETE_BOOKLIST);
    }

    @Test
    public void whenSelectingListMoviesAListOfMoviesIsShown() {
        ConsoleUI ui = createUIWithInput("d\n");
        ui.show();
        assertOutputIs(COMPLETE_MOVIELIST);
    }

    @Test
    public void checkingOutAnAvailableBookShouldResultInASuccessMessage() {
        ConsoleUI ui = createUIWithInput("b\n1\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + THANK_YOU_MSG);
    }

    @Test
    public void whenTheSecondBookIsCheckedOutItShouldNotAppearInTheBooklist() {
        ConsoleUI ui = createUIWithInput("b\n2\na\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG +
                        THANK_YOU_MSG + "\n" +
                        SELECT_OPTION_MSG +
                        BOOK1_ENTRY);
    }

    @Test
    public void whenCheckingOutAnAlreadyCheckedOutBookAMessageAppears() {
        ConsoleUI ui = createUIWithInput("b\n1\nb\n1\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                                SELECT_OPTION_MSG + SELECT_BOOKID_MSG + "\n" +
                                INVALID_BOOK_MESSAGE);
    }

    @Test
    public void whenProvidingAnInvalidBookIdAMessageAppears() {
        ConsoleUI ui = createUIWithInput("b\nFOO\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + "\n" + INVALID_BOOK_MESSAGE);
    }

    @Test
    public void whenSelectingAnInvalidBookToReturnAMessageShouldAppear() {
        ConsoleUI ui = createUIWithInput("c\n1\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + "\n" + NO_VALID_BOOK_TO_RETURN_MSG);
    }

    @Test
    public void whenTheFirstBookIsReturnedTheBookShouldAppearInTheListAgain() {
        ConsoleUI ui = createUIWithInput("b\n1\nc\n1\na\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                SELECT_OPTION_MSG + SELECT_BOOKID_MSG + VALID_RETURN_MSG + "\n" +
                SELECT_OPTION_MSG + COMPLETE_BOOKLIST);
    }

    @Test
    public void whenTheSecondBookIsReturnedTheBookShouldAppearInTheListAgain() {
        ConsoleUI ui = createUIWithInput("b\n2\nc\n2\na\n");
        ui.show();
        assertOutputIs(SELECT_BOOKID_MSG + THANK_YOU_MSG + "\n" +
                SELECT_OPTION_MSG + SELECT_BOOKID_MSG + VALID_RETURN_MSG + "\n" +
                SELECT_OPTION_MSG + COMPLETE_BOOKLIST);
    }
}
package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

public class ConsoleUITest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    private ConsoleUI createUIWithInput(String input) {
        SecureLibrary bookLibrary = new SecureLibrary<>(
            new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
            new Book("Animal Farm", "George Orwell", Year.of(1945))
        );
        Library movieLibrary = new Library<>(
            new Movie("E.T. the Extra-Terrestrial", "Steven Spielberg", Year.of(1982), Rating.UNRATED),
            new Movie("The Terminator", "James Cameron", Year.of(1984), Rating.of(9))
        );
        return new ConsoleUI(new ByteArrayInputStream((input + "k\n").getBytes()), bookLibrary, movieLibrary);
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        ConsoleUI ui = createUIWithInput("");
        ui.show();
        assertEquals(TestConstants.WELCOME_MESSAGE + TestConstants.MAIN_MENU + TestConstants.END_OF_OUTPUT, outContent.toString());
    }

    @Test
    public void whenSelectingAnInvalidOptionAMessageIsShown() {
        ConsoleUI ui = createUIWithInput("q\n");
        ui.show();
        assertOutputIs(TestConstants.INVALID_OPTION_MESSAGE);
    }

    private void assertOutputIs(String expected) {
        assertEquals(TestConstants.INITIAL_OUTPUT + expected + TestConstants.END_OF_OUTPUT, outContent.toString());
    }

    @Test
    public void afterSelectingAnInvalidOptionWeCanChooseAnotherOne() {
        ConsoleUI ui = createUIWithInput("q\nq\n");
        ui.show();
        assertOutputIs(TestConstants.INVALID_OPTION_MESSAGE + "\n" + TestConstants.SELECT_OPTION_MSG + TestConstants.INVALID_OPTION_MESSAGE);
    }

    @Test
    public void whenSelectingListBooksAListOfBooksIsShown() {
        ConsoleUI ui = createUIWithInput("a\n");
        ui.show();
        assertOutputIs(TestConstants.COMPLETE_BOOKLIST);
    }

    @Test
    public void whenSelectingListMoviesAListOfMoviesIsShown() {
        ConsoleUI ui = createUIWithInput("d\n");
        ui.show();
        assertOutputIs(TestConstants.COMPLETE_MOVIELIST);
    }

    @Test
    public void checkingOutAnAvailableMovieShouldResultInASuccessMessage() {
        ConsoleUI ui = createUIWithInput("e\n1\n");
        ui.show();
        assertOutputIs(TestConstants.SELECT_MOVIEID_MSG + TestConstants.THANK_YOU_MSG);
    }

    @Test
    public void whenCheckingOutAnAlreadyCheckedOutMovieAMessageAppears() {
        ConsoleUI ui = createUIWithInput("e\n1\ne\n1\n");
        ui.show();
        assertOutputIs(TestConstants.SELECT_MOVIEID_MSG + TestConstants.THANK_YOU_MSG + "\n" +
                                TestConstants.SELECT_OPTION_MSG + TestConstants.SELECT_MOVIEID_MSG + "\n" +
                                TestConstants.INVALID_MOVIE_MESSAGE);
    }

    @Test
    public void whenProvidingAnInvalidMovieIdAMessageAppears() {
        ConsoleUI ui = createUIWithInput("e\nFOO\n");
        ui.show();
        assertOutputIs(TestConstants.SELECT_MOVIEID_MSG + "\n" + TestConstants.INVALID_MOVIE_MESSAGE);
    }

    @Test
    public void whenSelectingAnInvalidMovieToReturnAMessageShouldAppear() {
        ConsoleUI ui = createUIWithInput("f\n1\n");
        ui.show();
        assertOutputIs(TestConstants.SELECT_MOVIEID_MSG + "\n" + TestConstants.NO_VALID_MOVIE_TO_RETURN_MSG);
    }


}
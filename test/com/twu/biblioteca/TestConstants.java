package com.twu.biblioteca;

public class TestConstants {
    public static final String SELECT_OPTION_MSG = "Please select an option: ";
    public static final String END_OF_OUTPUT = "\n" + SELECT_OPTION_MSG;
    public static final String MAIN_MENU = "a) List Books\nd) List Movies\ne) Check-out Movie\nf) Return Movie\ng) Login\nj) List Accountabilities for Books\nk) Quit\n";
    public static final String WELCOME_MESSAGE = "Welcome to Biblioteca!\n\n";
    public static final String INITIAL_OUTPUT = WELCOME_MESSAGE + MAIN_MENU + "\n" + SELECT_OPTION_MSG;
    public static final String INVALID_OPTION_MESSAGE = "Select a valid option!\n";
    public static final String INVALID_MOVIE_MESSAGE = "That movie is not available.\n";
    public static final String BOOK1_ENTRY = "(1) Brave New World, Aldous Huxley, 1932\n";
    public static final String BOOK2_ENTRY = "(2) Animal Farm, George Orwell, 1945\n";
    public static final String COMPLETE_BOOKLIST = BOOK1_ENTRY + BOOK2_ENTRY;
    public static final String COMPLETE_MOVIELIST = "(1) E.T. the Extra-Terrestrial, Steven Spielberg, 1982, UNRATED\n(2) The Terminator, James Cameron, 1984, 9\n";
    public static final String SELECT_MOVIEID_MSG = "Please select a movie ID: ";
    public static final String THANK_YOU_MSG = "Thank you! Enjoy the movie\n";
    public static final String NO_VALID_MOVIE_TO_RETURN_MSG = "That is not a valid movie to return.\n";
    public static final String LIST_BOOKS_LABEL = "List Books\n";
    public static final String QUIT_LABEL = "Quit\n";
}

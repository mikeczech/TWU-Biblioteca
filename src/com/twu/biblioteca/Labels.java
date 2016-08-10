package com.twu.biblioteca;

import java.util.function.Function;

class Labels {

    private Labels() {}

    static final String LOGIN = "Login";
    static final String LOGOUT = "Logout";
    static final String SHOW_USER_INFORMATION = "Show User Information";
    static final String LIST_ACCOUNTABILITIES_FOR_BOOKS = "List Accountabilities for Books";

    static final String WELCOME = "Welcome to Biblioteca!";
    static final String INVALID_OPTION = "Select a valid option!";
    static final Function<String, String> SUCCESSFUL_RETURN = s -> "Thank you for returning the " + s + ".";
    static final Function<String, String> UNSUCCESSFUL_RETURN = s -> "That is not a valid " + s + " to return.";
    static final Function<String, String> SUCCESSFUL_CHECKOUT = s -> "Thank you! Enjoy the " + s;
    static final Function<String, String> UNSUCCESSFUL_CHECKOUT = s -> "That " + s + " is not available.";
    static final Function<String, String> SELECT_ITEMID = s -> "Please select a " + s + " ID: ";

    static final String LIST_BOOKS = "List Books";
    static final String CHECK_OUT_BOOK = "Check-out Book";
    static final String RETURN_BOOK = "Return Book";
    static final String QUIT = "Quit";
    static final String LIST_MOVIES = "List Movies";
    static final String CHECK_OUT_MOVIE = "Check-out Movie";
    static final String RETURN_MOVIE = "Return Movie";

}

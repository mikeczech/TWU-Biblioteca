package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MenuTest {

    private static final String LIST_BOOKS_LABEL = "List Books\n";
    private static final String QUIT_LABEL = "Quit\n";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private Menu emptyMenu;
    private Menu menuWithOneOption;
    private Menu menuWithOptionThatRequiresAuthentication;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        emptyMenu = new Menu();

        menuWithOneOption = new Menu();
        menuWithOneOption.addOption("List Books", () -> System.out.println("All the books"));

        menuWithOptionThatRequiresAuthentication = new Menu();
        menuWithOptionThatRequiresAuthentication.addOptionWithLabel("List Books");
        menuWithOptionThatRequiresAuthentication.addOptionThatRequiresAuthentication("Quit", () -> System.out.println());
    }

    @After
    public void setDown() {
        UserSession.logout();
    }

    @Test
    public void menuStringReprShowsOptions() {
        assertEquals("a) " + LIST_BOOKS_LABEL, menuWithOneOption.toString());
    }

    @Test
    public void menuStringReprShowsMultipleOptions() {
        menuWithOneOption.addOptionWithLabel("Quit");

        assertEquals("a) " + LIST_BOOKS_LABEL +
                "b) " + QUIT_LABEL, menuWithOneOption.toString());
    }

    @Test
    public void menuStringReprShowsMultipleOptionsWithASpecificOrder() {
        emptyMenu.addOptionWithLabel("Quit");
        emptyMenu.addOptionWithLabel("List Books");

        assertEquals("a) " + QUIT_LABEL +
                "b) " + LIST_BOOKS_LABEL, emptyMenu.toString());
    }

    @Test
    public void selectingOptionShouldLeadToExpectedAction() throws IOException {
        menuWithOneOption.applyOptionWithId('a');

        assertEquals("All the books\n", outContent.toString());
    }

    @Test(expected = IOException.class)
    public void selectingUnknownOptionShouldFail() throws IOException {
        menuWithOneOption.applyOptionWithId('b');
    }

    @Test
    public void selectingSecondOptionLeadsToExpectedAction() throws IOException {
        menuWithOneOption.addOption("Quit", () -> System.out.println("Now we quit"));

        menuWithOneOption.applyOptionWithId('b');

        assertEquals("Now we quit\n", outContent.toString());
    }

    @Test
    public void optionThatRequiresAuthenticationShouldBeShownTheMenuIfTheUserIsLoggedIn() {
        UserSession.login("123-4567", "foobar");
        assertEquals("a) " +  LIST_BOOKS_LABEL +
                "b) " + QUIT_LABEL, menuWithOptionThatRequiresAuthentication.toString());
    }

    @Test
    public void optionThatRequiresAuthenticationShouldBeHiddenIfTheUserIsNotLoggedIn() {
        assertEquals("a) " +  LIST_BOOKS_LABEL, menuWithOptionThatRequiresAuthentication.toString());
    }

}
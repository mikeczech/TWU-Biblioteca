package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private Menu emptyMenu;
    private Menu menuWithOneOption;
    private Menu menuWithOptionThatRequiresAuthentication;
    private Menu menuWithOptionThatIsHiddenWhenAuthenticated;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        emptyMenu = new Menu();

        menuWithOneOption = new Menu();
        menuWithOneOption.addOption("List Books", () -> System.out.println("All the books"));

        menuWithOptionThatRequiresAuthentication = new Menu();
        menuWithOptionThatRequiresAuthentication.addOptionWithLabel("List Books");
        menuWithOptionThatRequiresAuthentication.addOptionThatRequiresAuthentication("Quit", () -> System.out.println());

        menuWithOptionThatIsHiddenWhenAuthenticated = new Menu();
        menuWithOptionThatIsHiddenWhenAuthenticated.addOptionWithLabel("List Books");
        menuWithOptionThatIsHiddenWhenAuthenticated.addOptionThatIsHiddenWhenAuthenticated("Quit", () -> System.out.println());
    }

    @After
    public void setDown() {
        UserSession.logout();
    }

    @Test
    public void menuStringReprShowsOptions() {
        assertEquals("a) " + TestConstants.LIST_BOOKS_LABEL, menuWithOneOption.toString());
    }

    @Test
    public void menuStringReprShowsMultipleOptions() {
        menuWithOneOption.addOptionWithLabel("Quit");

        assertEquals("a) " + TestConstants.LIST_BOOKS_LABEL +
                "b) " + TestConstants.QUIT_LABEL, menuWithOneOption.toString());
    }

    @Test
    public void menuStringReprShowsMultipleOptionsWithASpecificOrder() {
        emptyMenu.addOptionWithLabel("Quit");
        emptyMenu.addOptionWithLabel("List Books");

        assertEquals("a) " + TestConstants.QUIT_LABEL +
                "b) " + TestConstants.LIST_BOOKS_LABEL, emptyMenu.toString());
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
        assertEquals("a) " +  TestConstants.LIST_BOOKS_LABEL +
                "b) " + TestConstants.QUIT_LABEL, menuWithOptionThatRequiresAuthentication.toString());
    }

    @Test
    public void optionThatRequiresAuthenticationShouldBeHiddenIfTheUserIsNotLoggedIn() {
        assertEquals("a) " +  TestConstants.LIST_BOOKS_LABEL, menuWithOptionThatRequiresAuthentication.toString());
    }

    @Test(expected = IllegalStateException.class)
    public void aUserThatIsNotLoggedInCannotApplyAnOptionThatRequiresAuthentication() throws IOException {
       menuWithOptionThatRequiresAuthentication.applyOptionWithId('b');
    }

    @Test
    public void optionThatIsHiddenAfterAuthenticationShouldBeShownWhenUserIsNotLoggedIn() {
        assertEquals("a) " +  TestConstants.LIST_BOOKS_LABEL +
                "b) " + TestConstants.QUIT_LABEL, menuWithOptionThatIsHiddenWhenAuthenticated.toString());
    }

    @Test
    public void optionThatIsHiddenAfterAuthenticationShouldBeHiddenWhenUserIsLoggedIn() {
        UserSession.login("123-4567", "foobar");
        assertEquals("a) " +  TestConstants.LIST_BOOKS_LABEL, menuWithOptionThatIsHiddenWhenAuthenticated.toString());
    }

}
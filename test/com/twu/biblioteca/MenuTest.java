package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by mczech on 05/08/16.
 */
public class MenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void showMenu() {
        Menu menu = new Menu();
        menu.addOption("List Books");

        menu.show();

        assertEquals("a) List Books\n", outContent.toString());
    }

    @Test
    public void menuShowsMultipleAddedOptions() {
        Menu menu = new Menu();
        menu.addOption("List Books");
        menu.addOption("Quit");

        menu.show();

        assertEquals("a) List Books\n" +
                     "b) Quit\n", outContent.toString());
    }

    @Test
    public void menuShowsMultipleAddedOptionsWithASpecificOrder() {
        Menu menu = new Menu();
        menu.addOption("Quit");
        menu.addOption("List Books");

        menu.show();

        assertEquals("a) Quit\n" +
                "b) List Books\n", outContent.toString());
    }

    @Test
    public void selectionOfOptionShouldLeadTotheExpectedAction() throws IOException {
        Menu menu = new Menu();
        menu.addOption("List Books", () -> System.out.println("All the books"));
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        menu.processInput();

        assertEquals("All the books\n", outContent.toString());
    }

    @Test
    public void appliedActionShouldDependOnOption() throws IOException {
        Menu menu = new Menu();
        menu.addOption("Quit", () -> System.out.println("Now we quit"));
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        menu.processInput();

        assertEquals("Now we quit\n", outContent.toString());
    }

    @Test(expected = IOException.class)
    public void stringAsInputShouldFail() throws IOException {
        Menu menu = new Menu();
        menu.addOption("Quit");
        ByteArrayInputStream in = new ByteArrayInputStream("aba".getBytes());
        System.setIn(in);

        menu.processInput();
    }

    @Test(expected = IOException.class)
    public void selectingUnknownOptionShouldFail() throws IOException {
        Menu menu = new Menu();
        menu.addOption("Quit");
        ByteArrayInputStream in = new ByteArrayInputStream("b".getBytes());
        System.setIn(in);

        menu.processInput();
    }

    @Test(expected = IOException.class)
    public void givingEmptyInputShouldFail() throws IOException {
        Menu menu = new Menu();
        menu.addOption("Quit");
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        System.setIn(in);

        menu.processInput();
    }

    @Test
    public void selectingSecondOptionLeadsToExpectedAction() throws IOException {
        Menu menu = new Menu();
        menu.addOption("List Books", () -> System.out.println("List all the books"));
        menu.addOption("Quit", () -> System.out.println("Now we quit"));
        ByteArrayInputStream in = new ByteArrayInputStream("b".getBytes());
        System.setIn(in);

        menu.processInput();

        assertEquals("Now we quit\n", outContent.toString());
    }
}
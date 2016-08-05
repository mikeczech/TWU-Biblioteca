package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        menu.addOption("List Options");

        menu.show();

        assertEquals("a) List Options\n", outContent.toString());
    }

    @Test
    public void menuShowsMultipleAddedOptions() {
        Menu menu = new Menu();
        menu.addOption("List Options");
        menu.addOption("Quit");

        menu.show();

        assertEquals("a) List Options\n" +
                     "b) Quit\n", outContent.toString());
    }

    @Test
    public void menuShowsMultipleAddedOptionsWithASpecificOrder() {
        Menu menu = new Menu();
        menu.addOption("Quit");
        menu.addOption("List Options");

        menu.show();

        assertEquals("a) Quit\n" +
                "b) List Options\n", outContent.toString());
    }

    @Test
    public void chooseOption() {
        Menu menu = new Menu();
        menu.addOption("Hello", () -> System.out.println("Hello World"));
        ByteArrayInputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        menu.readInput();

        assertEquals("Hello World\n", outContent.toString());
    }

}
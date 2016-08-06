package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void showWelcomeMessage() {
        BibliotecaApp app = new BibliotecaApp();
        app.showWelcomeMessage();
        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    public void welcomeMessageIsSpecificText() {
        BibliotecaApp app = new BibliotecaApp();
        app.showWelcomeMessage();
        assertEquals("Welcome to Biblioteca!\n", outContent.toString());
    }

    @Test
    public void afterShowingTheWelcomeMessageTheMenuShouldAppear() {
        BibliotecaApp app = new BibliotecaApp();

        app.startAndThenQuit();

        assertEquals("Welcome to Biblioteca!\n\na) List Books\nb) Quit\n", outContent.toString());
    }

}
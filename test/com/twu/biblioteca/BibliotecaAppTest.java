package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

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
    public void listBooks() {
        BibliotecaApp app = new BibliotecaApp(new Book("", "", Year.of(0)));
        app.listBooks();
        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    public void emptyLibraryProducesNoOutput() {
        BibliotecaApp app = new BibliotecaApp();
        app.listBooks();
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    public void listedBookisBraveNewWorld() {
        BibliotecaApp app = new BibliotecaApp(new Book("Brave New World", "Aldous Huxley", Year.of(1932)));
        app.listBooks();
        assertEquals("(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void listedBookisAnimalFarm() {
        BibliotecaApp app = new BibliotecaApp(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        app.listBooks();
        assertEquals("(1) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void listingMultipleBooksProducesCorrectNumbering() {
        BibliotecaApp app = new BibliotecaApp(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)));
        app.listBooks();
        assertEquals("(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n" +
                     "(2) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }
}
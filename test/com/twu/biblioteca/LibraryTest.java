package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

/**
 * Created by mczech on 05/08/16.
 */
public class LibraryTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void listBooks() {
        Library lib = new Library(new Book("", "", Year.of(0)));
        lib.listBooks();
        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    public void emptyLibraryProducesNoOutput() {
        Library lib = new Library();
        lib.listBooks();
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    public void listedBookisBraveNewWorld() {
        Library lib = new Library(new Book("Brave New World", "Aldous Huxley", Year.of(1932)));
        lib.listBooks();
        assertEquals("(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void listedBookisAnimalFarm() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.listBooks();
        assertEquals("(1) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void listingMultipleBooksProducesCorrectNumbering() {
        Library lib = new Library(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.listBooks();
        assertEquals("(1) Brave New World, Aldous Huxley, 1932 [AVAILABLE]\n" +
                "(2) Animal Farm, George Orwell, 1945 [AVAILABLE]\n", outContent.toString());
    }

    @Test
    public void checkedOutBooksShouldBeMarkedAsCheckedOut() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.checkout(0);
        assertTrue(lib.isCheckedOut(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingOutAnUnknownBookShouldFail() {
        Library lib = new Library();
        lib.checkout(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingCheckedOutStatusOfUnknownBookShouldFail() {
        Library lib = new Library();
        lib.isCheckedOut(0);
    }

    @Test(expected = IllegalStateException.class)
    public void checkingOutACheckedOutBookShouldFail() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));

        lib.checkout(0);
        lib.checkout(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void returningAnUnknownBookShouldFail() {
        Library lib = new Library();
        lib.returnBook(0);
    }

    @Test
    public void returningACheckedOutBookShouldSucceed() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.checkout(0);

        lib.returnBook(0);

        assertFalse(lib.isCheckedOut(0));
    }

}
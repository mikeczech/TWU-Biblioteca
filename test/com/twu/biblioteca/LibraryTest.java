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
    public void emptyLibraryProducesNoOutput() {
        Library lib = new Library();
        assertEquals("", lib.toString());
    }

    @Test
    public void listedBookisBraveNewWorld() {
        Library lib = new Library(new Book("Brave New World", "Aldous Huxley", Year.of(1932)));
        assertEquals("(1) Brave New World, Aldous Huxley, 1932\n", lib.toString());
    }

    @Test
    public void listedBookisAnimalFarm() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        assertEquals("(1) Animal Farm, George Orwell, 1945\n", lib.toString());
    }

    @Test
    public void listingMultipleBooksProducesCorrectNumbering() {
        Library lib = new Library(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945)));
        assertEquals("(1) Brave New World, Aldous Huxley, 1932\n" +
                "(2) Animal Farm, George Orwell, 1945\n", lib.toString());
    }

    @Test
    public void checkedOutBooksShouldBeMarkedAsCheckedOut() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.checkoutBookWithId(0);
        assertTrue(lib.isCheckedOut(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingOutAnUnknownBookShouldFail() {
        Library lib = new Library();
        lib.checkoutBookWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingCheckedOutStatusOfUnknownBookShouldFail() {
        Library lib = new Library();
        lib.isCheckedOut(0);
    }

    @Test(expected = IllegalStateException.class)
    public void checkingOutACheckedOutBookShouldFail() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.checkoutBookWithId(0);

        lib.checkoutBookWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void returningAnUnknownBookShouldFail() {
        Library lib = new Library();
        lib.returnBookWithGivenId(0);
    }

    @Test
    public void returningACheckedOutBookShouldSucceed() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.checkoutBookWithId(0);

        lib.returnBookWithGivenId(0);

        assertFalse(lib.isCheckedOut(0));
    }

    @Test(expected = IllegalStateException.class)
    public void returningANonCheckedOutBookShouldFail() {
        Library lib = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        lib.returnBookWithGivenId(0);
    }

}
package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;

import static org.junit.Assert.*;

public class LibraryTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private Library libraryWithOneBook;
    private Library emptyLibrary;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        libraryWithOneBook = new Library(new Book("Animal Farm", "George Orwell", Year.of(1945)));
        emptyLibrary = new Library();
    }

    @Test
    public void checkedOutBooksShouldBeMarkedAsCheckedOut() {
        libraryWithOneBook.checkoutBookWithId(0);
        assertTrue(libraryWithOneBook.isCheckedOut(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingOutAnUnknownBookShouldFail() {
        emptyLibrary.checkoutBookWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingCheckedOutStatusOfUnknownBookShouldFail() {
        emptyLibrary.isCheckedOut(0);
    }

    @Test(expected = IllegalStateException.class)
    public void checkingOutACheckedOutBookShouldFail() {
        libraryWithOneBook.checkoutBookWithId(0);
        libraryWithOneBook.checkoutBookWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void returningAnUnknownBookShouldFail() {
        emptyLibrary.returnBookWithGivenId(0);
    }

    @Test
    public void returningACheckedOutBookShouldSucceed() {
        libraryWithOneBook.checkoutBookWithId(0);

        libraryWithOneBook.returnBookWithGivenId(0);

        assertFalse(libraryWithOneBook.isCheckedOut(0));
    }

    @Test(expected = IllegalStateException.class)
    public void returningANonCheckedOutBookShouldFail() {
        libraryWithOneBook.returnBookWithGivenId(0);
    }

}
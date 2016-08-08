package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.Date;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void bookProvidesTitle() {
        Book book = new Book("Brave New World", "", Year.of(0));
        assertEquals("Brave New World", book.getTitle());
    }

    @Test
    public void bookProvidesAuthor() {
        Book book = new Book("", "Aldous Huxley", Year.of(0));
        assertEquals("Aldous Huxley", book.getAuthor());
    }

    @Test
    public void bookProvidesPublicationYear() {
        Book book = new Book("", "", Year.of(1932));
        assertEquals(Year.of(1932), book.getPublicationYear());
    }

    @Test
    public void bookWithEqualPropertiesShouldBeEqual() {
        Book bookA = new Book("Brave New World", "Aldous Huxley", Year.of(1932));
        Book bookB = new Book("Animal Farm", "George Orwell", Year.of(1945));
        assertTrue(bookA.equals(bookB));
    }

}
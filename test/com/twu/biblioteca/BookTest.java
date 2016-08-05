package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.Date;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void bookProvidesTitle() {
        Book book = new Book("Brave New World", "");
        assertEquals("Brave New World", book.getTitle());
    }

    @Test
    public void bookProvidesAuthor() {
        Book book = new Book("", "Aldous Huxley");
        assertEquals("Aldous Huxley", book.getAuthor());
    }

    @Test
    public void bookProvidesPublicationYear() {
        Book book = new Book("", "", Year.of(1932));
        assertEquals(Year.of(1932), book.getPublicationYear());
    }


}
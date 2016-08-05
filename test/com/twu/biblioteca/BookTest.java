package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.Date;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void bookProvidesTitle() {
        Book book = new Book("Brave New World");
        assertEquals("Brave New World", book.getTitle());
    }


}
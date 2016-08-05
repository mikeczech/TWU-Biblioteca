package com.twu.biblioteca;

import java.time.Year;

public class Book {

    private String title;

    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String s, String s1, Year of) {

    }

    public String getTitle() {
            return title;
    }

    public String getAuthor() {
        return author;
    }

    public Year getPublicationYear() {
        return null;
    }
}

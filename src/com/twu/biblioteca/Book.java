package com.twu.biblioteca;

import java.time.Year;

public class Book {

    private String title;
    private String author;
    private Year publicationYear;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(String s, String s1, Year of) {
        this.publicationYear = of;
    }

    public String getTitle() {
            return title;
    }

    public String getAuthor() {
        return author;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }
}

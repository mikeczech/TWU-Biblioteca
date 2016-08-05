package com.twu.biblioteca;

import java.time.Year;

public class Book {

    private String title;
    private String author;
    private Year publicationYear;

    public Book(String title, String author, Year publicationYear) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
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

    public String toString() {
        return String.join(", ", title, author, publicationYear.toString());
    }
}

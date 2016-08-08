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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!title.equals(book.title)) return false;
        if (!author.equals(book.author)) return false;
        return publicationYear.equals(book.publicationYear);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + publicationYear.hashCode();
        return result;
    }
}

package com.twu.biblioteca;

public class Book {

    private String title;

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, String author) {
    }

    public String getTitle() {
            return title;
    }

    public String getAuthor() {
        return null;
    }
}

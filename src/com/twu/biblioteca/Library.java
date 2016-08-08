package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


public class Library {

    private List<Book> books = new ArrayList<>();
    private Set<Book> checkedOut = new HashSet<>();

    public Library(Book ...books) {
        for(Book b : books)
            this.books.add(b);
    }

    public void checkoutBookWithId(int bookId) {
        Book book = tryToGetBookById(bookId);
        if(isCheckedOut(book))
            throw new IllegalStateException("The book with the given ID is not available anymore.");
        checkOutBook(book);
    }

    private Book tryToGetBookById(int bookId) {
        try {
            return books.get(bookId);
        } catch(IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Unknown book ID");
        }
    }

    private boolean isCheckedOut(Book book) {
        return checkedOut.contains(book);
    }

    private void checkOutBook(Book book) {
        checkedOut.add(book);
    }

    public boolean isCheckedOut(int bookId) {
        Book book = tryToGetBookById(bookId);
        return isCheckedOut(book);
    }

    public void returnBookWithGivenId(int bookId) {
        Book book = tryToGetBookById(bookId);
        if(!isCheckedOut(book))
            throw new IllegalStateException("The book with the given ID is not checked out.");
        returnBook(book);
    }

    private void returnBook(Book book) {
        checkedOut.remove(book);
    }

    @Override
    public String toString() {
        StringBuilder libStrBuilder = new StringBuilder();
        IntStream.range(0, books.size()).forEach(i -> {
            if (!isCheckedOut(i))
                libStrBuilder.append("(" + (i + 1) + ") " + books.get(i) + "\n");
        });
        return libStrBuilder.toString();
    }

}

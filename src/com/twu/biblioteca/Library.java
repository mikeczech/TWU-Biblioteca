package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by mczech on 05/08/16.
 */
public class Library {

    private List<Book> books = new ArrayList<>();

    public Library(Book ...books) {
        for(Book b : books)
            this.books.add(b);
    }

    public void listBooks() {
        IntStream.range(0, books.size()).forEach(i ->
                System.out.println("(" + (i+1) + ") " + books.get(i) +  " [AVAILABLE]")
        );
    }

}

package com.twu.biblioteca;

import java.time.Year;

class Movie extends LibraryItem {

    private Rating rating;

    public Movie(String name, String director, Year publicationYear, Rating rating) {
        super(name, director, publicationYear);
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.join(", ", super.toString(), rating.toString());
    }

}

package com.twu.biblioteca;

import java.time.Year;

public abstract class LibraryItem {

    private final String name;
    private final String producer;
    private final Year publicationYear;

    public LibraryItem(String name, String producer, Year publicationYear) {
        this.name = name;
        this.producer = producer;
        this.publicationYear = publicationYear;
    }

    public String toString() {
        return String.join(", ", name, producer, publicationYear.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LibraryItem that = (LibraryItem) o;

        if (!name.equals(that.name)) return false;
        if (!producer.equals(that.producer)) return false;
        return publicationYear.equals(that.publicationYear);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + producer.hashCode();
        result = 31 * result + publicationYear.hashCode();
        return result;
    }

}

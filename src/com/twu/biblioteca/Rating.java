package com.twu.biblioteca;

public class Rating {

    public static final Rating UNRATED = new Rating();

    private static final int MAX = 10;
    private static final int MIN = 0;

    private int value;

    private Rating() {}

    public Rating(int value) {
        if(value > MAX || value < MIN)
            throw new IllegalArgumentException("Given value is outside range");
        this.value = value;
    }

    @Override
    public String toString() {
        if(this == UNRATED)
            return "UNRATED";
        return String.valueOf(value);
    }
}

package com.twu.biblioteca;

import java.util.*;
import java.util.stream.IntStream;


class Library<T extends LibraryItem> {

    private final List<T> items = new ArrayList<>();
    private final Set<T> checkedOut = new HashSet<>();

    public Library(T ... items) {
        Collections.addAll(this.items, items);
    }

    public void checkoutItemWithId(int itemId) {
        T item = tryToGetItemById(itemId);
        if(isCheckedOut(item))
            throw new IllegalStateException("The item with the given ID is not available anymore.");
        checkOutItem(item);
    }

    private T tryToGetItemById(int itemId) {
        try {
            return items.get(itemId);
        } catch(IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Unknown item ID");
        }
    }

    private boolean isCheckedOut(T item) {
        return checkedOut.contains(item);
    }

    private void checkOutItem(T item) {
        checkedOut.add(item);
    }

    public boolean isCheckedOut(int itemId) {
        T item = tryToGetItemById(itemId);
        return isCheckedOut(item);
    }

    public void returnItemWithId(int itemId) {
        T item = tryToGetItemById(itemId);
        if(!isCheckedOut(item))
            throw new IllegalStateException("The item with the given ID is not checked out.");
        returnItem(item);
    }

    private void returnItem(T item) {
        checkedOut.remove(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Library<?> library = (Library<?>) o;

        if (!items.equals(library.items)) return false;
        return checkedOut.equals(library.checkedOut);

    }

    @Override
    public int hashCode() {
        int result = items.hashCode();
        result = 31 * result + checkedOut.hashCode();
        return result;
    }

    @Override

    public String toString() {
        StringBuilder libStrBuilder = new StringBuilder();
        IntStream.range(0, items.size()).forEach(i -> {
            if (!isCheckedOut(i))
                libStrBuilder.append("(").append(i + 1).append(") ").append(items.get(i)).append("\n");
        });
        return libStrBuilder.toString();
    }

}

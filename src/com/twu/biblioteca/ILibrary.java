package com.twu.biblioteca;

public interface ILibrary {
    void checkoutItemWithId(int itemId);

    boolean isCheckedOut(int itemId);

    void returnItemWithId(int itemId);
}

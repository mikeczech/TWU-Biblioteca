package com.twu.biblioteca;

public class SecureLibrary<T extends LibraryItem> implements ILibrary {

    protected Library<T> library;

    public SecureLibrary(T ... items) {
        library = new Library<>(items);
    }

    @Override
    public void checkoutItemWithId(int itemId) {
        if(!UserSession.isLoggedIn())
            throw new IllegalStateException("You must be logged in to check-out an item!");
        library.checkoutItemWithId(itemId);
    }

    @Override
    public boolean isCheckedOut(int itemId) {
        return library.isCheckedOut(itemId);
    }

    @Override
    public void returnItemWithId(int itemId) {
        if(!UserSession.isLoggedIn())
            throw new IllegalStateException("You must be logged in to return an item!");
        library.returnItemWithId(itemId);
    }

}

package com.twu.biblioteca;

import java.util.Map;

public interface ILibrary {
    void checkoutItemWithId(int itemId);

    boolean isCheckedOut(int itemId);

    void returnItemWithId(int itemId);

    Map<Integer, UserSession> getAccountabilities();
}

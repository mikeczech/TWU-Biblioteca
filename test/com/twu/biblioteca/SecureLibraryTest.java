package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SecureLibraryTest {

    private SecureLibrary secureLibraryWithTwoBooks;

    @Before
    public void setUp() {
        secureLibraryWithTwoBooks = new SecureLibrary<>(
                new Book("Animal Farm", "George Orwell", Year.of(1945)),
                new Book("How to lie with statistics", "Darrell Huff", Year.of(1954))
        );
        secureLibraryWithTwoBooks.library.checkoutItemWithId(1);
    }

    @After
    public void setDown() {
        UserSession.logout();
    }

    @Test(expected = IllegalStateException.class)
    public void whenNotLoggedInCheckoutShouldFail() {
        secureLibraryWithTwoBooks.checkoutItemWithId(0);
    }

    @Test
    public void whenLoggedInCheckoutShouldSucceed() {
        UserSession.login("123-4567", "foobar");
        secureLibraryWithTwoBooks.checkoutItemWithId(0);
        assertTrue(secureLibraryWithTwoBooks.isCheckedOut(0));
    }

    @Test(expected = IllegalStateException.class)
    public void whenNotLoggedInReturnShouldFail() {
        secureLibraryWithTwoBooks.returnItemWithId(1);
    }

    @Test
    public void whenLoggedInReturnShouldSucceed() {
        UserSession.login("123-4567", "foobar");
        secureLibraryWithTwoBooks.returnItemWithId(1);
    }

    @Test
    public void whenAUserChecksOutABookSheShouldBeAccountableForIt() {
        UserSession.login("123-4567", "foobar");
        secureLibraryWithTwoBooks.checkoutItemWithId(0);
        assertEquals(new HashMap<Integer, UserSession>() {{put(0, UserSession.getSession());}},
                secureLibraryWithTwoBooks.getAccountabilities());
    }

    @Test
    public void whenAUserReturnsABookSheShouldNotBeAccountableAnymore() {
        UserSession.login("123-4567", "foobar");
        secureLibraryWithTwoBooks.checkoutItemWithId(0);

        secureLibraryWithTwoBooks.returnItemWithId(0);

        assertEquals(new HashMap<Integer, UserSession>(), secureLibraryWithTwoBooks.getAccountabilities());
    }

}
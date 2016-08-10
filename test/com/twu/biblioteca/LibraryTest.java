package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LibraryTest {

    private Library libraryWithTwoBooks;
    private Library emptyLibrary;

    @Before
    public void setUp() {
        libraryWithTwoBooks = new Library<>(
                new Book("Brave New World", "Aldous Huxley", Year.of(1932)),
                new Book("Animal Farm", "George Orwell", Year.of(1945))
        );
        emptyLibrary = new Library<>();
    }

    @After
    public void setDown() {
        UserSession.logout();
    }

    @Test
    public void checkedOutBooksShouldBeMarkedAsCheckedOut() {
        libraryWithTwoBooks.checkoutItemWithId(0);
        assertTrue(libraryWithTwoBooks.isCheckedOut(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingOutAnUnknownBookShouldFail() {
        emptyLibrary.checkoutItemWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkingCheckedOutStatusOfUnknownBookShouldFail() {
        emptyLibrary.isCheckedOut(0);
    }

    @Test(expected = IllegalStateException.class)
    public void checkingOutACheckedOutBookShouldFail() {
        libraryWithTwoBooks.checkoutItemWithId(0);
        libraryWithTwoBooks.checkoutItemWithId(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void returningAnUnknownBookShouldFail() {
        emptyLibrary.returnItemWithId(0);
    }

    @Test
    public void returningACheckedOutBookShouldSucceed() {
        libraryWithTwoBooks.checkoutItemWithId(0);

        libraryWithTwoBooks.returnItemWithId(0);

        assertFalse(libraryWithTwoBooks.isCheckedOut(0));
    }

    @Test(expected = IllegalStateException.class)
    public void returningANonCheckedOutBookShouldFail() {
        libraryWithTwoBooks.returnItemWithId(0);
    }

    @Test
    public void whenTheFirstItemIsReturnedTheItemShouldAppearInTheListAgain() {
        libraryWithTwoBooks.checkoutItemWithId(0);

        libraryWithTwoBooks.returnItemWithId(0);

        assertEquals(TestConstants.COMPLETE_BOOKLIST, libraryWithTwoBooks.toString());
    }

    @Test
    public void whenTheSecondItemIsReturnedTheItemShouldAppearInTheListAgain() {
        libraryWithTwoBooks.checkoutItemWithId(1);

        libraryWithTwoBooks.returnItemWithId(1);

        assertEquals(TestConstants.COMPLETE_BOOKLIST, libraryWithTwoBooks.toString());
    }

    @Test
    public void whenTheSecondItemIsCheckedOutItShouldNotAppearInTheList() {
        libraryWithTwoBooks.checkoutItemWithId(1);
        assertEquals(TestConstants.BOOK1_ENTRY, libraryWithTwoBooks.toString());
    }

}
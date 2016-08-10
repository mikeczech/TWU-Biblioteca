package com.twu.biblioteca;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserSessionTest {

    @After
    public void setDown() {
        UserSession.logout();
    }

    @Test
    public void whenLoginSucceedsTheUserIsLoggedIn() {
        UserSession.login("123-4567", "foobar");
        assertTrue(UserSession.isLoggedIn());
    }

    @Test
    public void aLoggedInUserThatLogsOutShouldNotBeLoggedInAnymore() {
        UserSession.login("123-4567", "foobar");
        UserSession.logout();
        assertFalse(UserSession.isLoggedIn());
    }

    @Test(expected = IllegalStateException.class)
    public void doubleLoginShouldFail() {
        UserSession.login("123-4567", "foobar");
        UserSession.login("245-62421", "barfoo");
    }

    @Test
    public void whenDonaldLogsInHeCanAccessHisInformation() {
        UserSession.login("123-4567", "foobar");
        UserSession session = UserSession.getSession();
        assertEquals("Donald Trump", session.getName());
        assertEquals("trump@dumb.com", session.getEmail());
        assertEquals("123456", session.getPhone());
    }

    @Test
    public void whenHillaryLogsInSheCanAccessHerInformation() {
        UserSession.login("523-4242", "barfoo");
        UserSession session = UserSession.getSession();
        assertEquals("Hillary Clinton", session.getName());
        assertEquals("clinton@dumb.com", session.getEmail());
        assertEquals("654321", session.getPhone());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivingAWrongPasswordTheAuthenticationShouldFail() {
        UserSession.login("123-4567", "wrongpassword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivingAnUnknownLibraryNumberTheAuthenticationShouldFail() {
        UserSession.login("32423dwd32", "foobar");
    }

    @Test(expected = IllegalStateException.class)
    public void whenUserIsNotLoggedInAccessingTheUserSessionShouldFail() {
        UserSession.getSession();
    }

}
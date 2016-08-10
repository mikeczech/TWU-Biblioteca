package com.twu.biblioteca;

import java.util.HashMap;
import java.util.Map;

public class UserSession {

    private static boolean loggedIn = false;
    private static UserSession session;

    private static final Map<String, String> userToPassword = new HashMap<>();
    private  static final Map<String, UserSession> userToSession = new HashMap<>();
    static {
        userToPassword.put("123-4567", "foobar");
        userToSession.put("123-4567", new UserSession("Donald Trump", "trump@dumb.com", "123456"));

        userToPassword.put("523-4242", "barfoo");
        userToSession.put("523-4242", new UserSession("Hillary Clinton", "clinton@foo.com", "654321"));
    }

    private String name;
    private String email;
    private String phone;

    private UserSession(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static UserSession getSession() {
        if(!loggedIn)
            throw new IllegalStateException("Not logged in.");
        return session;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void logout() {
        loggedIn = false;
    }

    public static void login(String libraryNumber, String password) {
        if(loggedIn)
            throw new IllegalStateException("You are already logged in.");
        if(!userToPassword.containsKey(libraryNumber))
            throw new IllegalArgumentException("Unknown library number");
        if(!userToPassword.get(libraryNumber).equals(password))
            throw new IllegalArgumentException("Wrong password");
        session = userToSession.get(libraryNumber);
        loggedIn = true;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

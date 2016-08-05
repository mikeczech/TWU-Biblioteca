package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Created by mczech on 05/08/16.
 */
public class BibliotecaAppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void showWelcomeMessage() {
        BibliotecaApp app = new BibliotecaApp();
        app.showWelcomeMessage();
        assertFalse(outContent.toString().isEmpty());
    }

}
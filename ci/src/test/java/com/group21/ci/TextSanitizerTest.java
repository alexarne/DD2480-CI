package com.group21.ci;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextSanitizerTest {

    /*
     * Positive Case, for a valid input, no changes are expected
     */

    @Test
    public void testSanitize_PositiveCase() {
        String input = "This is a test";
        String expected = "This is a test";
        assertEquals(expected, TextSanitizer.sanitize(input));
    }

    /*
     * Negative test, if all character are invalid, it returns an empty string
     */

    @Test
    public void testSanitize_NegativeCase() {
        String input = "!@#$%^&*()";
        String expected = "";
        assertEquals(expected, TextSanitizer.sanitize(input));
    }

    

}

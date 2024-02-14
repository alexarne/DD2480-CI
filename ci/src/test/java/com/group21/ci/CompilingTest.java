package com.group21.ci;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

import org.junit.Assert.*;

public class CompilingTest {
    
    @Test
    public void testCreatedFilesFound() throws IOException {
        String pathToClasses = "target/classes/com/group21/ci/";
        
        assertTrue(new File(pathToClasses + "Config.class").exists());
        assertTrue(new File(pathToClasses + "ContinuousIntegrationServer.class").exists());
        assertTrue(new File(pathToClasses + "RepositoryInfo.class").exists());
        assertTrue(new File(pathToClasses + "RepositoryTester.class").exists());
        assertTrue(new File(pathToClasses + "StatusSender.class").exists());
        assertTrue(new File(pathToClasses + "TextSanitizer.class").exists());
    
    }

}

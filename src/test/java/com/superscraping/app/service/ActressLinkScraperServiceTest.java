/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Norio
 */
public class ActressLinkScraperServiceTest {
    
    public ActressLinkScraperServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of scarapingContents method, of class ActressLinkScraperService.
     */
    @Test
    public void testScarapingContents() {
        System.out.println("scarapingContents");
        String linkUrl = "";
        ActressLinkScraperService instance = new ActressLinkScraperService();
        List<String> result = instance.getAvailableInitial();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}

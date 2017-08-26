/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.Actresses;
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
public class ActressScraperServiceTest {
    
    public ActressScraperServiceTest() {
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
     * Test of getActress method, of class ActressScraperService.
     */
    @Test
    public void testGetActress() {
        System.out.println("getActress");
        String url = "http://www.dmm.co.jp/digital/videoa/-/actress/=/keyword=a/page=11/";
        ActressScraperService instance = new ActressScraperService();
        Actresses expResult = null;
        Actresses result = instance.getActress(url);
        System.out.println("aaa");
    }

    
}

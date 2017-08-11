/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LinkScrapingServiceTest {
    
    public LinkScrapingServiceTest() {
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
     * Test of getItemLinks method, of class LinkScrapingService.
     */
    @Test
    public void testGetTotalNum() {
        System.out.println("getItemLinks");
        String itemsLinkUrl = "http://www.dmm.co.jp/digital/videoa/-/list/=/sort=ranking/";
        LinkScrapingService instance = new LinkScrapingService(itemsLinkUrl);
        
        try {
            List<String> links = instance.getLinkStart();
        } catch (Exception ex) {
            Logger.getLogger(LinkScrapingServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("hogehoge");
    }    
}

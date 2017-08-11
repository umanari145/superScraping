/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.DmmItem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Norio
 */
public class ItemScrapingServiceTest {
    
    public ItemScrapingServiceTest() {
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
     * Test of getDmmItem method, of class ItemScrapingService.
     */
    @Test
    public void testGetDmmItem() {
        System.out.println("getDmmItem");
        //適当にDMMから1商品を選ぶ
        String itemLink = "http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=oycvr00003/?i3_ref=list&i3_ord=1";

        ItemScrapingService instance = new ItemScrapingService(itemLink,true);
        DmmItem expResult = null;
        DmmItem result = instance.getDmmItem();
        //assertEquals(expResult, result);
        System.out.print("final");
    }
    
}

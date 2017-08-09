/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.DmmItem;
import com.superscraping.entity.ItemLink;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Norio
 */
public class DmmItemServiceTest {
    
    public DmmItemServiceTest() {
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
     * Test of getDmmItem method, of class DmmItemService.
     */
    @Test
    public void testGetDmmItem() {
        System.out.println("getDmmItem");
        //適当にDMMから1商品を選ぶ
        ItemLink itemLink = new ItemLink("http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=41hodv021062/?i3_ref=search&i3_ord=1");

        DmmItemService instance = new DmmItemService();
        DmmItem expResult = null;
        DmmItem result = instance.getDmmItem(itemLink);
        //assertEquals(expResult, result);
        System.out.print("final");
    }
    
}

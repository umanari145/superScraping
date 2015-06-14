/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class PageGetterTest {
    
    public PageGetterTest() {
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
     * Test of getContentes method, of class PageGetter.
     */
    @Test
    public void testGetContentes() {
        System.out.println("getContentes");
        List<String> contentsLinkListAll = new ArrayList<>();
        contentsLinkListAll.add("http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=h_067nade00981/");
        contentsLinkListAll.add("http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=118abp00277/?dmmref=recomend1&i3_ref=recommend&i3_ord=2");
        contentsLinkListAll.add("http://www.dmm.co.jp/digital/videoa/-/detail/=/cid=118abp00109/?dmmref=recomend2&i3_ref=recommend&i3_ord=2");
        
        PageGetter instance = new PageGetter();
        ArrayList<HashMap<String, String>> expResult = null;
        ArrayList<HashMap<String, String>> result = instance.getContentes(contentsLinkListAll);
        
        System.out.print("tast");
    }
    
}

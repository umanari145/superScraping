/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.link;

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
public class LinkGetterTest {
    
    public LinkGetterTest() {
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
     * Test of getContentsLink method, of class LinkGetter.
     */
    @Test
    public void testGetContentsLink() {
        System.out.println("getContentsLink");
        String linkUrl = "http://www.dmm.co.jp/digital/videoa/-/list/=/article=keyword/id=1018/sort=ranking/";
        LinkGetter instance = new LinkGetter();
        //List<String> links = instance.getContentsLink(linkUrl);
        // TODO review the generated test code and remove the default call to fail.
        System.out.print("aa");
    }
}

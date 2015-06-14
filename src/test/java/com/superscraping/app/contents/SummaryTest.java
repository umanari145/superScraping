/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.contents;

import com.superscraping.entity.DmmProduct;
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
public class SummaryTest {
    
    public SummaryTest() {
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
     * Test of getSettedProduct method, of class Summary.
     */
    @Test
    public void testGetSettedProduct() {
        System.out.println("getSettedProduct");
        DmmProduct product = new DmmProduct();
        String fieldName = "内容";
        String textData = "うのです。 ※ 配信方法kok\nokoご注意ください。";
        Summary instance = new Summary();
        DmmProduct result = instance.getSettedProduct(product, fieldName, textData);
        String resultStr = result.getSummary();
        String expect ="うのです。 ";
        assertEquals(expect, resultStr);
    }
    
}

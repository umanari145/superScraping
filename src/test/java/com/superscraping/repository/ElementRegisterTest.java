/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.entity.Actresses;
import com.superscraping.entity.Tags;
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
public class ElementRegisterTest {
    
    public ElementRegisterTest() {
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
     * Test of getTagData method, of class ElementRegister.
     */
    @Test
    public void testGetTagData() {
        System.out.println("getTagData");
        String tagStr = "";
        ElementRegister instance = new ElementRegister();
        List<Tags> expResult = null;
        List<Tags> result = instance.getTagData(tagStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGirlsData method, of class ElementRegister.
     */
    @Test
    public void testGetGirlsData() {
        System.out.println("getGirlsData");
        List<String> girlIdList = null;
        ElementRegister instance = new ElementRegister();
        List<Actresses> expResult = null;
        List<Actresses> result = instance.getGirlsData(girlIdList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registGirl method, of class ElementRegister.
     */
    @Test
    public void testRegistGirl() {
        System.out.println("registGirl");
        Actresses girl = null;
        ElementRegister instance = new ElementRegister();
        instance.registGirl(girl);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registRelateProductAndTag method, of class ElementRegister.
     */
    @Test
    public void testRegistRelateProductAndTag() {
        System.out.println("registRelateProductAndTag");
        Integer productId = null;
        List<Tags> tagList = null;
        ElementRegister instance = new ElementRegister();
        instance.registRelateProductAndTag(productId, tagList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registRelateProductAndGirl method, of class ElementRegister.
     */
    @Test
    public void testRegistRelateProductAndGirl() {
        System.out.println("registRelateProductAndGirl");
        Integer productId = null;
        List<Actresses> girlList = null;
        ElementRegister instance = new ElementRegister();
        instance.registRelateProductAndGirl(productId, girlList);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

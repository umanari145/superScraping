/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

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
public class RegistControllerTest {
    
    public RegistControllerTest() {
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
     * Test of registContents method, of class RegistController.
     */
    @Test
    public void testRegistContents() {
        System.out.println("registContents");
        List<Map<String, String>> contentDetail = null;
        RegistController instance = new RegistController();
        instance.registContents(contentDetail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntity method, of class RegistController.
     */
    @Test
    public void testGetEntity() {
        System.out.println("getEntity");
        RegistController instance = new RegistController();
        List<com.superscraping.entity.DmmItemEntity> expResult = null;
        List<com.superscraping.entity.DmmItemEntity> result = instance.getEntity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductRegister method, of class RegistController.
     */
    @Test
    public void testGetProductRegister() {
        System.out.println("getProductRegister");
        RegistController instance = new RegistController();
        ItemRepository expResult = null;
        ItemRepository result = instance.getProductRegister();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProductRegister method, of class RegistController.
     */
    @Test
    public void testSetProductRegister() {
        System.out.println("setProductRegister");
        ItemRepository productRegister = null;
        RegistController instance = new RegistController();
        instance.setProductRegister(productRegister);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getElementRegister method, of class RegistController.
     */
    @Test
    public void testGetElementRegister() {
        System.out.println("getElementRegister");
        RegistController instance = new RegistController();
        ElementRegister expResult = null;
        ElementRegister result = instance.getElementRegister();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setElementRegister method, of class RegistController.
     */
    @Test
    public void testSetElementRegister() {
        System.out.println("setElementRegister");
        ElementRegister elementRegister = null;
        RegistController instance = new RegistController();
        instance.setElementRegister(elementRegister);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

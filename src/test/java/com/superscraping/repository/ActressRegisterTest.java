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
public class ActressRegisterTest {
    
    public ActressRegisterTest() {
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
     * Test of registContents method, of class ActressRegister.
     */
    @Test
    public void testRegistContents() {
        System.out.println("registContents");
        List<Map<String, String>> contentDetail = null;
        ActressRegister instance = new ActressRegister();
        instance.registContents(contentDetail);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEntity method, of class ActressRegister.
     */
    @Test
    public void testGetEntity() {
        System.out.println("getEntity");
        ActressRegister instance = new ActressRegister();
        List<com.superscraping.entity.DmmItemEntity> expResult = null;
        List<com.superscraping.entity.DmmItemEntity> result = instance.getEntity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getElementRegister method, of class ActressRegister.
     */
    @Test
    public void testGetElementRegister() {
        System.out.println("getElementRegister");
        ActressRegister instance = new ActressRegister();
        ElementRegister expResult = null;
        ElementRegister result = instance.getElementRegister();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setElementRegister method, of class ActressRegister.
     */
    @Test
    public void testSetElementRegister() {
        System.out.println("setElementRegister");
        ElementRegister elementRegister = null;
        ActressRegister instance = new ActressRegister();
        instance.setElementRegister(elementRegister);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.entity.DmmProduct;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Norio
 */
public class ControllerTest {
    
    public ControllerTest() {
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
     * Test of main method, of class Controller.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Controller.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class Controller.
     */
    @Test
    public void testStart() {
        System.out.println("start");
        Controller instance = new Controller();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class Controller.
     */
    @Test @Ignore
    public void testInit() {
        System.out.println("init");
        Controller instance = new Controller();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of action method, of class Controller.
     */
    @Test @Ignore
    public void testAction() {
        System.out.println("action");
        Controller instance = new Controller();
        instance.action();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContentsLink method, of class Controller.
     */
    @Test
    public void testGetContentsLink() {
        
        System.out.println("一覧リンクの取得");
        Controller instance = new Controller();
        //テストフラグをセット
        instance.setTestFlg(true);
        instance.init();
        instance.getContentsLink();

        System.out.println("詳細データの取得");
        List<Map<String, String>> result = instance.getContentsDetail();

        System.out.println("DB登録用に展開");
        instance.registDB(result);
        
    }
}
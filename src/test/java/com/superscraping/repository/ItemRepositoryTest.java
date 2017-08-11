/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.entity.DmmItem;
import java.util.List;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Norio
 */
public class ItemRepositoryTest {
    
    public ItemRepositoryTest() {
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

    @Test
    public void testRegist(){
        ItemRepository itemrepo = new ItemRepository();
        DmmItem dmmItem = new DmmItem();
        dmmItem.setProductName("sample2");
        
        itemrepo.startTransaction();
        itemrepo.registItem(dmmItem);
        itemrepo.transactionCommit();
        
        List<DmmItem> dmmItems = itemrepo.getAll();
        System.out.print("aaaa");
    }
    
}

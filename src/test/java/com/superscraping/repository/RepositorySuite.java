/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Norio
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.superscraping.repository.ItemRepositoryTest.class, com.superscraping.repository.DBbaseTest.class, com.superscraping.repository.ElementRegisterTest.class, com.superscraping.repository.RegistControllerTest.class, com.superscraping.repository.ActressRegisterTest.class})
public class RepositorySuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}

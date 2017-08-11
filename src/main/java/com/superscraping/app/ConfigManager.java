/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

/**
 *
 * @author Norio
 */
public class ConfigManager {
    
    /**
     * 単一ページのテストフラグ
     */
    public static final boolean IS_SINGLE_ITEM_TEST = true;

    /**
     * 単一ページのテストページ数
     */
    public static final int TEST_ITEM_COUNT = 5;

           
    /**
     * 全体ページのテストフラグ
     */
    public static final boolean IS_CONTENTS_TEST = true;
    
    /**
     * 全体ページのテストページ数
     */
    public static final int TEST_PAGE_COUNT = 2; 

    /**
     * スタートのURL
     */
    public static final String SITE_URL = "http://www.dmm.co.jp/digital/videoa/-/list/=/article=keyword/id=1018/limit=30/sort=ranking/"; 
    
    /**
     * 女優リストのURL
     */
    public static final String ACTRESS_SITE_URL="http://www.dmm.co.jp/digital/videoa/-/actress/=/"; 
 
    /**
     * 女優ページのテストフラグ
     */
    public static final boolean IS_ACTRESS_CONTENTS_TEST = false;
    
    
    public ConfigManager(){}
}

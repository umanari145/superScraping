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
     * 単一一覧ページのテストフラグ
     */
    public static final boolean IS_SINGLE_CONTENTS_TEST = false;

    /**
     * 単一一覧ページのテストリンク数
     */
    public static final int TEST_CONTENTS_COUNT=10; 
           
    /**
     * 全体ページのテストフラグ
     */
    public static final boolean IS_CONTENTS_TEST = true;
    
    /**
     * 全体ページのテストページ数
     */
    public static final int TEST_PAGE_COUNT = 6;

    /**
     * スタートのURL
     */
    public static final String SITE_URL="http://www.dmm.co.jp/digital/videoa/-/list/=/article=keyword/id=1018/limit=120/sort=ranking/"; 
    

    public ConfigManager(){}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import java.util.List;
import java.util.Map;

/**
 * スクレイピングのインターフェイス
 * @author Norio
 */
public interface ScraperImpl {
    
    public List<Map<String,String>> scarapingContents(String linkUrl);
  
}

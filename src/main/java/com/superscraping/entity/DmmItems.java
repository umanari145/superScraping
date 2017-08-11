/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * DmmItemのファーストクラスコレクション化したもの
 * @author Norio
 */
public class DmmItems {
    
    /**
     * Itemのコレクション
     */
    private List<DmmItem> dmmItems;
    
    
    /**
     * インスタンス
     */
    public DmmItems(){
        dmmItems = new ArrayList<DmmItem>();
    }
    
    /**
     * DMMitemを追加
     * @param dmmItem dmmItem 
     */
    public void addDmmItems(DmmItem dmmItem) {
        dmmItems.add(dmmItem);
    }
    
    /**
     * item数
     * 
     * @return item数
     */
    public int size() {
        return dmmItems.size();
    }
    
    /**
     * item数
     * 
     * @param i index
     * @return dmmItem
     */
    public DmmItem get(Integer i) {
        return dmmItems.get(i);
    }
}

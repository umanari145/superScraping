/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.DmmItem;
import com.superscraping.repository.ItemRepository;

/**
 * 単一商品のデータ登録
 *
 * @author Norio
 */
public class ItemRegistService {

    private final ItemRepository itemRepository;

    /**
     * コンストラクタ
     *
     */
    public ItemRegistService() {
        itemRepository = new ItemRepository();
    }

    public void registItem(DmmItem dmmItem){
        
    }
  
}

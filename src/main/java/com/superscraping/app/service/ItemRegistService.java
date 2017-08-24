/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.DmmItem;
import com.superscraping.entity.DmmItems;
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

    /**
     * 複数itemの一括登録(bulkInsertあるにはあるがそんなに高速化がきかないっぽいのでそのままで・・)
     * 
     * @param dmmItems 
     */
    public void registItems(DmmItems dmmItems) {
        
        itemRepository.startTransaction();
        int dmmSize = dmmItems.size();
        for (int i = 0; i < dmmSize; i++) {
            DmmItem dmmItem = dmmItems.get(i);
            itemRepository.registItem(dmmItem);
        }
       
        itemRepository.transactionCommit();
    }


}

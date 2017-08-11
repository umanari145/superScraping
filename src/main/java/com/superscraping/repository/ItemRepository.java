/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.entity.DmmItem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Norio
 */
public class ItemRepository extends DBbase {

    private EntityManager em;
        
    public void registItem(DmmItem dmmItem){
        super.create(dmmItem);
    }
    
    
    /**
     * 全アイテムの取得
     * 
     * @return DMMの商品リスト
     */
    public List<DmmItem> getAll() {
        this.em = this.getEm();
        List<DmmItem> productList = new ArrayList<DmmItem>();
        List<DmmItem> tmpList = em.createQuery(" SELECT i FROM DmmItem i WHERE i.deleteFlg = 0 ", DmmItem.class)
                                       .getResultList();
        if( tmpList.size() > 0 ) {
            productList = tmpList;
        } 
        return productList;
    }
    
    /**
     * 全アイテムの取得
     * 
     * @return DMMの商品リスト
     */
    public List<DmmItem> getExistProductCodeList() {
        this.em = this.getEm();
        
        List<DmmItem> itemList = new LinkedList<DmmItem>();
        List<DmmItem> tmpList = em.createQuery(" SELECT p.productCode FROM DmmItem p WHERE p.deleteFlg = 0 ", DmmItem.class)
                                       .getResultList();
        if( tmpList.size() > 0 ) {
            itemList = tmpList;
        } 
        return itemList;
    }
    

    /**
     * 同一品番の商品が存在するか否か
     * 
     * @param productCode 品番
     * @return true(存在する) /false(存在しない)
     */
    public boolean isSameCodeProduct(String productCode ) {
        this.em = this.getEm();
        List<DmmItem> tmpList = em.createQuery(" SELECT p FROM DmmProduct p WHERE p.deleteFlg = 0 and p.productCode = :productCode ", DmmItem.class)
                .setParameter("productCode", productCode)
                .getResultList();
        return  tmpList.size() > 0 ;
    }
    


}

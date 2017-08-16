/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.entity.DmmItemEntity;
import java.util.List;
import java.util.Map;

/**
 * 登録用のインターフェイス
 * @author Norio
 */
public interface RegistImpl {
    
    /**
     * 商品データの登録
     * 
     * @param contentDetail List.Map形式の商品データ 
     */
    public void registContents(List<Map<String, String>> contentDetail);
    
    /**
     * エンティティの取得
     * 
     * @return エンティティのリスト 
     */
    public List<DmmItemEntity> getEntity();
}

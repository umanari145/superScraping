/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.app.RegistImpl;
import com.superscraping.entity.BaseEntity;

import com.superscraping.entity.DmmItemEntity;
import com.superscraping.entity.Actress;
import com.superscraping.entity.Tags;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
public class RegistController implements RegistImpl {
   
    @Getter
    @Setter
    private ItemRepository productRegister;

    @Getter
    @Setter
    private ElementRegister elementRegister;

    public RegistController() {
        productRegister = new ItemRepository();
        elementRegister = new ElementRegister();
    }

    @Override
    public void registContents(List<Map<String, String>> contentDetail) {

        contentDetail.stream().forEach(contents -> {
            BaseEntity tmpProduct = productRegister.mapToEntity(contents);

            DmmItemEntity product = (DmmItemEntity) tmpProduct;

            //同一品番のデータがすでに登録されていないかをチェックする
            if (product.getProductCode() != null && product.getProductCode().length() > 0
                    && productRegister.isSameCodeProduct(product.getProductCode()) == false) {
                productRegister.startTransaction();
                elementRegister.startTransaction();
                
                //商品の保存
                productRegister.create(product);
                productRegister.getFlush();
                //タグリストへの変換
                List<Tags> tagList = elementRegister.getTagData(product.getGenre());                
                
                //タグの保存
                if( tagList.size() >0 ){
                    elementRegister.registRelateProductAndTag(product.getId(), tagList);
                }                
                //女優リストへの保存
                if( product.getActressIdList() != null && !product.getActressIdList().isEmpty()){
                    List<Actress> girlList = elementRegister.getGirlsData(product.getActressIdList());
                    //女優データの保存
                    if( girlList.size() >0){
                        elementRegister.registRelateProductAndGirl(product.getId(),girlList);                
                    }
                }
                //コミット
                productRegister.transactionCommit();
                elementRegister.transactionCommit();
                
            }
        });
    }

    @Override
    public List<DmmItemEntity> getEntity() {
        return productRegister.getAll();
    }

}

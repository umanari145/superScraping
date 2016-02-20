/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.app.RegistImpl;
import com.superscraping.entity.BaseEntity;

import com.superscraping.entity.DmmProduct;
import com.superscraping.entity.Girls;
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
    private ProductRegister productRegister;

    @Getter
    @Setter
    private ElementRegister elementRegister;

    public RegistController() {
        productRegister = new ProductRegister();
        elementRegister = new ElementRegister();
    }

    @Override
    public void registContents(List<Map<String, String>> contentDetail) {

        contentDetail.stream().forEach(contents -> {
            BaseEntity tmpProduct = productRegister.mapToEntity(contents);

            DmmProduct product = (DmmProduct) tmpProduct;

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
                 List<Girls> girlList = elementRegister.getGirlsData(product.getActress());
                //女優データの保存
                if( girlList.size() >0){
                    elementRegister.registRelateProductAndGirl(product.getId(),girlList);                
                }
                //コミット
                productRegister.transactionCommit();
                elementRegister.transactionCommit();
                
            }
        });
    }

    @Override
    public List<DmmProduct> getEntity() {
        return productRegister.getAll();
    }

}

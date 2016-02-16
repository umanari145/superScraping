/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.app.RegistImpl;
import com.superscraping.entity.BaseEntity;

import com.superscraping.entity.DmmProduct;
import com.superscraping.entity.ItemTags;
import com.superscraping.entity.Tags;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
@Stateless
public class RegistController implements RegistImpl {

    @Getter
    @Setter
    private ProductRegister productRegister;

    @Getter
    @Setter
    private TagRegister tagRegister;

    public RegistController() {
        productRegister = new ProductRegister();
        tagRegister = new TagRegister();
    }

    @Override
    public void registContents(List<Map<String, String>> contentDetail) {

        contentDetail.stream().forEach(contents -> {
            BaseEntity tmpProduct = productRegister.mapToEntity(contents);

            DmmProduct product = (DmmProduct) tmpProduct;

            //同一品番のデータがすでに登録されていないかをチェックする
            if (product.getProductCode() != null && product.getProductCode().length() > 0
                    && productRegister.isSameCodeProduct(product.getProductCode()) == false) {
                //商品の保存
                productRegister.create(product);
                //タグリストへの変換
                List<Tags> tagList = tagRegister.getTagData(product.getGenre());                
                //タグの保存
                tagRegister.registRelateProductAndTag(product.getId(), tagList);
            }
        });
    }

    @Override
    public List<DmmProduct> getEntity() {
        return productRegister.getAll();
    }

}

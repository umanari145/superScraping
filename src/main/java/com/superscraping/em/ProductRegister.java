/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.superscraping.entity.BaseEntity;
import com.superscraping.entity.DmmProduct;
import com.superscraping.em.helper.MapEntityConverter;
import com.superscraping.util.Utility;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;

/**
 *
 * @author Norio
 */
public class ProductRegister extends DBbase implements MapEntityConverter{

    private EntityManager em;
    
    /**
     * 全アイテムの取得
     * 
     * @return DMMの商品リスト
     */
    public List<DmmProduct> getAll() {
        this.em = this.getEm();
        List<DmmProduct> productList = new ArrayList<DmmProduct>();
        List<DmmProduct> tmpList = em.createQuery(" SELECT p FROM DmmProduct p WHERE p.deleteFlg = 0 ", DmmProduct.class)
                                       .getResultList();
        if( tmpList.size() > 0 ) {
            productList = tmpList;
        } 
        return productList;
    }

    /**
     * 同一品番の商品が存在するか否か
     * 
     * @param productCode 品番
     * @return true(存在する) /false(存在しない)
     */
    public boolean isSameCodeProduct(String productCode ) {
        this.em = this.getEm();
        List<DmmProduct> tmpList = em.createQuery(" SELECT p FROM DmmProduct p WHERE p.deleteFlg = 0 and p.productCode = :productCode ", DmmProduct.class)
                .setParameter("productCode", productCode)
                .getResultList();
        return  tmpList.size() > 0 ;
    }
    
    @Override
    public BaseEntity mapToEntity(Map<String, String> contentsMap) {
        DmmProduct product = new DmmProduct();

        contentsMap.forEach((key, value) -> {
            switch (key) {
                case "出演者":
                    product.setActress(value);
                    break;
                case "品番":
                    product.setProductCode(value);
                    break;
                case "ジャンル":
                    //&nbsp;が2つ分の置換はこれで行う
                    product.setGenre(value.replace("\u00a0\u00a0", " "));
                    break;
                case "レーベル":
                    product.setLabel(value);
                    break;
                case "メーカー":
                    product.setMaker(value);
                    break;
                case "productName":
                    product.setProductName(value);
                    break;
                case "商品発売日":
                    Date releaseDate = Utility.strToDate(value);
                    product.setReleaseDate(releaseDate);
                    break;
                case "summary":
                    String summary = convertSummary(value);
                    product.setSummary(summary);
                    break;
                case "pictureUrl":
                    product.setPictureUrl(value);
                    break;
            }
        });

        return product;
    }
    
    public String convertSummary(String textData) {
        String beforeRegex = "(.*?)※ 配信方法(.*?)ご注意ください。";
        //改行を含む場合はこのプロパティがいる
        Pattern p = Pattern.compile(beforeRegex, Pattern.DOTALL);
        Matcher m = p.matcher(textData);
        String afterRegex = "$1";
        String textData2 = m.replaceAll(afterRegex);
        return textData2;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import com.superscraping.util.Utility;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Norio
 */
public class DmmConverter extends MapEntityConverter {
    
    @Override
    public BaseProduct mapToEntity(Map<String, String> contentsMap) {
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
                    product.setGenre(value);
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

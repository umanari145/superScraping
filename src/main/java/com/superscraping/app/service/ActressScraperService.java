/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.em.helper.ScraperHelper;
import com.superscraping.entity.Actress;
import com.superscraping.entity.Actresses;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Norio
 */
public class ActressScraperService  {

    @Getter
    @Setter
    private Integer fieldOrder;

    
    public ActressScraperService(){
        this.fieldOrder = 0;
    }
    
    
    /**
     * 女優リストを取得する
     * 
     * @param url
     * @return 女優リスト
     */
    public Actresses getActress(String url){
        Document doc = ScraperHelper.getDocument(url);
        Elements elTd = getTableElements(doc);
        Actresses actresses = getActresses(elTd);
        return actresses;
    }
   
    /**
     * table形式データの取得(HTMLで判別することが不可能)
     *
     * @param doc docオブジェクト
     * @return テーブルElement
     */
    private Elements getTableElements(Document doc) {
        Elements elTd = doc.getElementsByClass("act-box-100").first().select("li");
        if (elTd == null) {
            return null;
        }
        return elTd;
    }          
       
    /**
     * 女優リストの取得
     * 
     * @param elTd 要素(複数)
     * @return 女優リスト
     */
    private Actresses getActresses(Elements elTd) {
        Actresses actressess = new Actresses();
        elTd.stream().map((el) -> getActressProp(el))
                .filter((actress) -> (actress != null))
                .forEach((actress) -> actressess.addActresses(actress));
        return actressess;
    }

    /**
     * 女優の属性を取得
     * 
     * @param 要素 el
     * @return 女優
     */
    private Actress getActressProp(Element el) {
        Actress actress = new Actress();
        actress.setDmmActressId(getActressId(el));
        if ( actress.getDmmActressId() != null) {
            countUpFieldOrder();            
            actress.setName(getActressname(el));
            actress.setInitialOrder(fieldOrder);
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "actressName {0}", actress.getName());
        return actress;
    }
    
    /**
     * 順番をカウントアップする
     */
    private void countUpFieldOrder(){
        Integer fieldOrder = this.fieldOrder;
        fieldOrder++;
        this.fieldOrder = fieldOrder;
    }
    
    
    /**
     * 女優名を取得
     * 
     * @param el 要素
     * @return 女優名
     */
    private String getActressname(Element el){
        return el.select("img").first().attr("alt");
    }
    
    /**
     * 女優Idの取得
     * 
     * @param el 要素
     * @return 女優id
     */
    private Integer getActressId(Element el) {
        String linkData = el.getElementsByTag("a").first().attr("href");
        String actressId = ScraperHelper.getExtract("id=(\\d*)", linkData);
        return Integer.parseInt(actressId);
    }
}

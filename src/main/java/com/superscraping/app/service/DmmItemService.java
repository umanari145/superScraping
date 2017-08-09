/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.app.ConfigManager;
import com.superscraping.app.ScraperImpl;
import com.superscraping.entity.DmmItem;
import com.superscraping.entity.Girl;
import com.superscraping.entity.Girls;
import com.superscraping.entity.ItemLink;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Norio
 */
public class DmmItemService implements ScraperImpl {

    /**
     * DMMページのテーブル要素のindex メーカーのindex
     */
    public static final int makerTableIndex = 15;

    /**
     * DMMページのテーブル要素のindex ラベルのindex
     */
    public static final int labelTableIndex = 17;
    
    /**
     * 進捗を表すために現在何商品を獲得しているかの値
     */
    private Integer proceedContentsCount = 0;

    private int pageCount;

    public DmmItemService() {
    }

    /**
     * 1ページあたりのリンク
     *
     * @param linkUrl　スタートのリンク
     * @param i カウント数
     * @return リンク集
     */
    @Override
    public List<Map<String, String>> scarapingContents(String linkUrl, int i) {
        //リンクからコンテンツを集める
        List<Map<String, String>> contentsList = new ArrayList<>();
        return contentsList;
    }

    /**
     * itemLinkからdmmのItemを取得
     * 
     * @param itemLink itemのLinkオブジェクト
     * @return dmmエンティティ
     */
    public DmmItem getDmmItem(ItemLink itemLink) {
        Document doc = itemLink.getDocument();
        DmmItem dmmItem = getDmmItem(doc);
        return dmmItem;
    } 
    
            /**
     * Docオブジェクトからそれぞれテキストデータを取り出す
     *
     * @param doc 商品のdocオブジェクト
     * @return 単品データのDmmitem
     */
    private DmmItem getDmmItem(Document doc) {

        DmmItem dmmItem = new DmmItem();
        //商品名
        dmmItem.setProductName(getProductName(doc));
        
//        this.proceedContentsCount++;
//        int proceedContentsCount2 = this.pageCount * 120 + this.proceedContentsCount;
//        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " , get{0}", new Object[]{proceedContentsCount2});
//        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " title {0} ",dmmItem.getProductName());

        //要約
        dmmItem.setSummary(getSummary(doc));
        //拡大画像
        dmmItem.setPictureUrl(getPicture(doc));
        //女優Idリスト
        dmmItem.setGirls(getGirls(doc));     
        //テーブル系のデータ
        Elements elTd = getTableElements(doc);
        dmmItem.setMaker(getDmmElementFromTd(elTd, makerTableIndex));
        dmmItem.setLabel(getDmmElementFromTd(elTd, labelTableIndex));
        return dmmItem;
    }

    /**
     * 商品名の取得
     *
     * @param doc docオブジェクト
     * @return 商品名の取得
     */
    private String getProductName(Document doc) {
        Element title = doc.getElementById("title");
        return title.text();
    }

    /**
     * 商品要約情報の取得
     *
     * @param doc　docオブジェクト
     * @return 商品要約
     */
    private String getSummary(Document doc) {
        Elements summaryElement = doc.getElementsByClass("lh4");
        return summaryElement.text();
    }

    /**
     * table形式データの取得(HTMLで判別することが不可能)
     *
     * @param doc docオブジェクト
     * @return テーブルElement
     */
    private Elements getTableElements(Document doc) {
        StringBuilder tableStr = new StringBuilder();
        Elements elTd = doc.select("table[class=mg-b20]").first().select("td");
        
        //デバッグ用
        for (int i = 0; i < elTd.size(); i++) {
            System.out.println(i + " " + elTd.get(i).text());
        }

        return elTd;
    }
    
    /**
     * テーブル要素からテキストデータを抽出
     * 
     * @param elTd テーブル要素
     * @param i インデックス
     * @return itemの要素
     */
    private String getDmmElementFromTd(Elements elTd, int i){
        return elTd.get(i).text();
    }


        /**
     * 女優idを取得する
     *
     * @param doc 要素
     * @return 女優Idを_でつなぐ
     */
    private Girls getGirls(Document doc) {
        Elements girlsLinks = doc.getElementById("performer").getElementsByTag("a");
        String actressIdCsv = null;
        List<String> actressIdList = new ArrayList<>();
        Girls girls = new Girls();
        
        for (Element girlLink : girlsLinks) {
            Integer girlId = getGirlIdFromGirlLink(girlLink);
            if (girlId != null ) {
                Girl girl = new Girl(girlId);
                girls.addGirls(girl);
            }
        }
        return girls;
    }

    /**
     * 女優のLinkオブジェクトからidを抽出
     * 
     * @param girlLink リンクオブジェクト
     * @return 女優id 
     */
    public Integer getGirlIdFromGirlLink(Element girlLink) {       

        Integer girlId = null;
        Pattern p = Pattern.compile("id=(\\d*)");
        Matcher m = p.matcher(girlLink.getElementsByTag("a").first().attr("href"));

        if (m.find() && m.group(1) != null) {
            girlId =Integer.parseInt(m.group(1));
        }
        return girlId;
    }


    /**
     * 商品画像を取得
     *
     * @param doc ドキュメントオブジェクト
     * @return 拡大画像のURL
     */
    private String getPicture(Document doc) {
        StringBuilder pictureStr = new StringBuilder();
        Elements pictureElement = doc.select("[name=package-image]");
        String pictureUrl = "";
        if (pictureElement.size() > 0) {
            pictureUrl = pictureElement.get(1).attr("href");
        }
        return pictureUrl;
    }

    
}

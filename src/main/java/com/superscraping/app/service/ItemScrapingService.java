/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.entity.DmmItem;
import com.superscraping.entity.Girl;
import com.superscraping.entity.Girls;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 単一商品のデータ取得
 *
 * @author Norio
 */
public class ItemScrapingService {

    /**
     * ドキュメントリンク
     */
    private final String itemLink;
    
    /**
     * デバッグモード
     */
    private final boolean isDebug;
    
    /**
     * コンストラクタ
     *
     * @param itemLink 単一のリンク
     */
    public ItemScrapingService(String itemLink) {
        this.itemLink = itemLink;
        this.isDebug = false;
    }

    public ItemScrapingService(String itemLink, boolean isDebug) {
        this.itemLink = itemLink;
        this.isDebug = isDebug;
    }
        
    /**
     * リンクからdocmentオブジェクトを返す
     *
     * @return リンクのドキュメントオブジェクト
     */
    public Document getDocument() {
        URL url = null;
        Document doc = null;
        try {
            url = new URL(this.itemLink);
            doc = Jsoup.parse(url, 3000);
        } catch (MalformedURLException ex) {
            Logger.getLogger(this.itemLink).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.itemLink).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    /**
     * itemLinkからdmmのItemを取得
     *
     * @return the com.superscraping.entity.DmmItem
     */
    public DmmItem getDmmItem() {
        Document doc = getDocument();
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
        //要約
        dmmItem.setSummary(getSummary(doc));
        //拡大画像
        dmmItem.setPictureUrl(getPicture(doc));
        //女優Idリスト
        dmmItem.setGirls(getGirls(doc));
        
        //テーブル(イレギュラー)系のデータ
        Elements elTd = getTableElements(doc);
        
        dmmItem.setMaker(getStrByIrregularElement(elTd,"メーカー："));
        dmmItem.setLabel(getStrByIrregularElement(elTd,"レーベル："));
        dmmItem.setProductCode(getStrByIrregularElement(elTd,"品番："));

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
        Elements elTd = doc.select("table[class=mg-b20]").first().select("td");
        //デバッグ用
        if(this.isDebug) for (int i = 0; i < elTd.size(); i++) System.out.println(i + " " + elTd.get(i).text());
        return elTd;
    }

    /**
     * テーブル要素から文字列を与えてその要素を取得する
     * 
     * @param elTd テーブル要素
     * @param targetStr　対象文字列
     * @return 抽出された文字列
     */
    private String getStrByIrregularElement(Elements elTd, String targetStr) {
        Integer makeElementIndex = getElementIndex(elTd, targetStr);        
        String extractStr = getDmmElementFromTd(elTd, makeElementIndex);
        return extractStr;
    }
    
    /**
     * 要素の番号を取得
     *
     * @param elTd テーブル要素
     * @param itemEleStr 要素文字列
     * @return
     */
    public Integer getElementIndex(Elements elTd, String itemEleStr) {
        Integer indexNumber = null;
        for (int i = 0; i < elTd.size(); i++) {
            if (elTd.get(i).text().equals(itemEleStr)) {
                indexNumber = i + 1;
                break;
            }
        }
        return indexNumber;
    }
    
    /**
     * テーブル要素からテキストデータを抽出
     *
     * @param elTd テーブル要素
     * @param i インデックス
     * @return itemの要素
     */
    private String getDmmElementFromTd(Elements elTd, int i) {
        return elTd.get(i).text();
    }

    /**
     * 女優idを取得する
     *
     * @param doc 要素
     * @return 女優Idを_でつなぐ
     */
    private Girls getGirls(Document doc) {
        Girls girls = new Girls();
        
        Element ele = doc.getElementById("performer");
        if (ele == null) return girls;
        
        Elements girlsLinks = ele.getElementsByTag("a");
        if (girlsLinks == null) return girls;
        
        for (Element girlLink : girlsLinks) {
            Integer girlId = getGirlIdFromGirlLink(girlLink);
            if (girlId != null) {
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
    private Integer getGirlIdFromGirlLink(Element girlLink) {

        Integer girlId = null;
        Pattern p = Pattern.compile("id=(\\d*)");
        Matcher m = p.matcher(girlLink.getElementsByTag("a").first().attr("href"));

        if (m.find() && m.group(1) != null) {
            girlId = Integer.parseInt(m.group(1));
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
        Elements pictureElement = doc.select("[name=package-image]");
        String pictureUrl = "";
        if (pictureElement.size() > 0) {
            pictureUrl = pictureElement.get(1).attr("href");
        }
        return pictureUrl;
    }

}

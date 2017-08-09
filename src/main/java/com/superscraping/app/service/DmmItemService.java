/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.app.ConfigManager;
import com.superscraping.app.ScraperImpl;
import com.superscraping.entity.DmmItem;
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
     * 商品データをマップ形式に変換する
     *
     * @param contentsMap key=>value型のマップデータ
     * @param contentsDetail 商品詳細データ
     * @return フィールド→値形式のMap
     */
    private Map<String, String> convertContentsDetail(Map<String, String> contentsHashMap, String contentsDetail) {
        contentsDetail = contentsDetail.replaceAll("：\n", ":");
        String[] contentsColumn = contentsDetail.split("\n");

        for (String singleContentsColumn : contentsColumn) {
            String[] contentsDetailData = singleContentsColumn.split(":");

            try {
                contentsHashMap.put(contentsDetailData[0], contentsDetailData[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, " error {0} ", e.getMessage());
            }
        }
        return contentsHashMap;
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
        // String actressIdCsv = getActressIdFromContents(doc);
        // if (actressIdCsv != null) {
        //      contentsHashMap.put("actressId", actressIdCsv);
        //}

        //テーブル系のデータ
        //StringBuilder tableStr = getContentsFromTableTag(doc);
        //convertContentsDetail(contentsHashMap, tableStr.toString());

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
     * table形式データの取得
     *
     * @param doc docオブジェクト
     * @return それぞれのデータを文字列にて取得
     */
    private StringBuilder getContentsFromTableTag(Document doc) {
        StringBuilder tableStr = new StringBuilder();
        Element table = doc.select("table[class=mg-b20]").first();
        Elements elTd = table.select("td");
        for (Element el : elTd) {
            tableStr.append(el.text()).append("\n");
        }
        return tableStr;
    }

    /**
     * 女優idを取得する
     *
     * @param doc 要素
     * @return 女優Idを_でつなぐ
     */
    private String getActressIdFromContents(Document doc) {
        Element el2 = doc.getElementById("performer");
        String actressIdCsv = null;
        if (el2 != null) {

            Elements els = el2.getElementsByTag("a");
            List<String> actressIdList = new ArrayList<>();
            for (Element el3 : els) {
                String tmp = el3.getElementsByTag("a").first().attr("href");
                String regex = "id=(\\d*)";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(tmp);
                //リンクデータを取得する
                if (m.find() && m.group(1) != null) {
                    String actressId = null;
                    actressId = m.group(1);
                    actressIdList.add(actressId);
                }
            }

            if (actressIdList.size() > 0) {
                actressIdCsv = actressIdList.stream().collect(Collectors.joining("_"));
            }
        }
        return actressIdCsv;
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

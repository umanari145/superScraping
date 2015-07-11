/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.link;

import com.superscraping.app.ConfigManager;
import com.superscraping.app.ScraperImpl;
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
import java.util.regex.Pattern;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Norio
 */
public class DmmScraper implements ScraperImpl {

    public DmmScraper() {
    }

    /**
     * 1ページあたりのリンク
     *
     * @param linkUrl　スタートのリンク
     * @return リンク集
     */
    @Override
    public List<Map<String, String>> scarapingContents(String linkUrl) {
        //リンクを集める  
        List<String> contentsLink = this.getContentsFromHtml(linkUrl);
        //コンテンツを集める
        List<Map<String, String>> contensDetailList = this.getHtmlContents(contentsLink);

        return contensDetailList;
    }

    /**
     * 実際にHTMLをパースしてリンクを取得
     *
     * @param linkUrl トップのリンク
     * @return リンク集
     */
    private List<String> getContentsFromHtml(String linkUrl) {
        List<String> contentsLinkList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(linkUrl).get();
            Elements eles = document.getElementsByAttributeValueMatching("href", Pattern.compile("^.*detail/=/cid.*$"));
            int testloopCnt = 0;
            for (Iterator<Element> iterator = eles.iterator(); iterator.hasNext();) {
                testloopCnt++;
                Element contentsLinkEle = iterator.next();
                Attributes contentsLinkAttr = contentsLinkEle.attributes();
                String link = contentsLinkAttr.get("href");
                contentsLinkList.add(link);

                if (ConfigManager.IS_CONTENTS_TEST && testloopCnt > 1) {
                    break;
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }

        return contentsLinkList;
    }

    /**
     * 商品リンクからフィールド→値形式のMapのコンテンツデータを取得
     *
     * @param contentsLink 単一商品のリンク
     * @return 商品ごとの情報をList.Map形式で取得
     */
    private List<Map<String, String>> getHtmlContents(List<String> contentsLink) {

        List<Map<String, String>> contentsMap = new ArrayList<>();
        for (String link : contentsLink) {
            String contentsDetail = getSingleContentsDetail(link);
            Map<String, String> tmpMap = convertContentsDetail(contentsDetail);
            contentsMap.add(tmpMap);
        }
        return contentsMap;
    }

    /**
     * リンクから単一商品のHTMLを解析
     *
     * @param contentsUrl　単一商品のURL
     * @return 商品データの文字列(:\nでつなぐ)
     */
    private String getSingleContentsDetail(String contentsUrl) {
        StringBuilder contentsDetail = null;
        try {
            contentsDetail = new StringBuilder();
            URL url = new URL(contentsUrl);
            Document doc = Jsoup.parse(url, 3000);
            contentsDetail = getHTMLAttribute(doc);

        } catch (MalformedURLException ex) {
            Logger.getLogger(DmmScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DmmScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contentsDetail.toString();
    }

    /**
     * 商品データをマップ形式に変換する
     * 
     * @param contentsDetail 商品詳細データ
     * @return フィールド→値形式のMap
     */
    private Map<String, String> convertContentsDetail(String contentsDetail) {
        contentsDetail = contentsDetail.replaceAll("：\n", ":");
        String[] contentsColumn = contentsDetail.split("\n");

        Map<String, String> contentsHashMap = new HashMap<>();
        for (String singleContentsColumn : contentsColumn) {
            String[] contentsDetailData = singleContentsColumn.split(":");
            contentsHashMap.put(contentsDetailData[0], contentsDetailData[1]);
        }
        return contentsHashMap;
    }

    /**
     * Docオブジェクトからそれぞれテキストデータを取り出す
     * 
     * @param doc 商品のdocオブジェクト
     * @return 文字列
     */
    private StringBuilder getHTMLAttribute(Document doc) {
        StringBuilder contentsDetail = new StringBuilder();

        StringBuilder titleStr = getTitile(doc);
        contentsDetail.append(titleStr);

        StringBuilder summaryStr = getSummary(doc);
        contentsDetail.append(summaryStr);

        StringBuilder tableStr = getContentsFromTableTag(doc);
        contentsDetail.append(tableStr);

        return contentsDetail;
    }

    /**
     * 商品名の取得
     * 
     * @param doc docオブジェクト
     * @return 商品名の取得
     */
    private StringBuilder getTitile(Document doc) {
        StringBuilder titleStr = new StringBuilder();
        Element title = doc.getElementById("title");
        titleStr.append("productName").append("：\n");
        titleStr.append(title.text()).append("\n");
        return titleStr;
    }

    /**
     * 商品要約情報の取得
     * 
     * @param doc　docオブジェクト
     * @return 商品要約
     */
    private StringBuilder getSummary(Document doc) {
        StringBuilder summaryStr = new StringBuilder();
        Elements summaryElement = doc.getElementsByClass("lh4");
        summaryStr.append("summary").append("：\n");
        summaryStr.append(summaryElement.text()).append("\n");
        return summaryStr;
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

}

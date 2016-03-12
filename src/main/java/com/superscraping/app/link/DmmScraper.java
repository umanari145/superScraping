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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    /**
     * 総商品数
     */
    private Integer totalItemCount;

    /**
     * 進捗を表すために現在何商品を獲得しているかの値
     */
    private Integer proceed = 0;

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
        //(1ページ目)リンクを集める 
        boolean hasTotalAmountGet;
        hasTotalAmountGet = true;
        List<String> contentsLink1 = this.getContentsFromHtml(linkUrl, hasTotalAmountGet);

        //2ページ目のリンクを集める(総商品数を集める都合上1ページの処理と分ける必要性あり)
        float loopCounttmp = totalItemCount / 120;
        int loopCount = (int) Math.ceil(loopCounttmp);
        List<String> contentsLink2 = new ArrayList<>();
        if (loopCount >= 2) {
            for (Integer i = 2; i <= loopCount; i++) {
                String linkUrl2 = String.format("%s/page=%d/", linkUrl, i);
                hasTotalAmountGet = false;
                List<String> contentsLinktmp = this.getContentsFromHtml(linkUrl2, hasTotalAmountGet);
                if (contentsLinktmp.size() > 0) {
                    contentsLink2.addAll(contentsLinktmp);
                }
                if (ConfigManager.IS_CONTENTS_TEST == true && ConfigManager.TEST_PAGE_COUNT <= i) {
                    break;
                }
            }
            contentsLink1.addAll(contentsLink2);
        }
        //リンクからコンテンツを集める
        List<Map<String, String>> contentsList = this.getHtmlContents(contentsLink1);

        return contentsList;
    }

    /**
     * 実際にHTMLをパースしてリンクを取得
     *
     * @param linkUrl トップのリンク
     * @param hasTotalAmountGet 総商品数を取得するかいなか
     * @return リンク集
     */
    private List<String> getContentsFromHtml(String linkUrl, boolean hasTotalAmountGet) {
        List<String> contentsLinkList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(linkUrl).get();
            Elements eles = document.getElementsByAttributeValueMatching("href", Pattern.compile("^.*detail/=/cid.*$"));
            int testloopCnt = 0;

            //総商品数の取得
            if (hasTotalAmountGet == true) {
                totalItemCount = getItemTotalCount(document);
            }

            for (Iterator<Element> iterator = eles.iterator(); iterator.hasNext();) {
                testloopCnt++;
                Element contentsLinkEle = iterator.next();
                Attributes contentsLinkAttr = contentsLinkEle.attributes();
                String link = contentsLinkAttr.get("href");
                contentsLinkList.add(link);

                if (ConfigManager.IS_SINGLE_CONTENTS_TEST && ConfigManager.TEST_CONTENTS_COUNT < testloopCnt) {
                    break;
                }
            }

        } catch (IOException e) {
            e.getMessage();
        }

        return contentsLinkList;
    }

    /**
     * 総商品数の出力
     *
     * @param document ドキュメントオブジェクト
     * @return 総商品数
     */
    private Integer getItemTotalCount(Document document) {
        Integer tmpTotalItemCount = 0;
        String totalItemCountElementString = document.select("div[class=list-boxcaptside list-boxpagenation] p").first().text();

        String regex = "(.*?)タイトル中.*?";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(totalItemCountElementString);

        if (m.find()) {
            String totalItemCountStr = m.group(1);
            totalItemCountStr = totalItemCountStr.replaceAll(",", "");
            tmpTotalItemCount = Integer.parseInt(totalItemCountStr);
        }
        return tmpTotalItemCount;
    }

    /**
     * 商品リンクからフィールド→値形式のMapのコンテンツデータを取得
     *
     * @param contentsLink 単一商品のリンク
     * @return 商品ごとの情報をList.Map形式で取得
     */
    private List<Map<String, String>> getHtmlContents(List<String> contentsLink) {

        List<Map<String, String>> contentsList = new ArrayList<>();
        for (String link : contentsLink) {
            Map<String, String> contentsDetailMap = getSingleContentsDetail(link);
            contentsList.add(contentsDetailMap);
        }
        return contentsList;
    }

    /**
     * リンクから単一商品のHTMLを解析
     *
     * @param contentsUrl　単一商品のURL
     * @return 商品データのMap
     */
    private Map<String, String> getSingleContentsDetail(String contentsUrl) {
        Map<String, String> contentsDetailMap = new HashMap<>();
        try {
            URL url = new URL(contentsUrl);
            Document doc = Jsoup.parse(url, 3000);
            contentsDetailMap = getSingcleContentsMap(doc);
            contentsDetailMap.put("productUrl", contentsUrl);

        } catch (MalformedURLException ex) {
            Logger.getLogger(DmmScraper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DmmScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contentsDetailMap;
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
            contentsHashMap.put(contentsDetailData[0], contentsDetailData[1]);
        }
        return contentsHashMap;
    }

    /**
     * Docオブジェクトからそれぞれテキストデータを取り出す
     *
     * @param doc 商品のdocオブジェクト
     * @return 単品諸品データのMap
     */
    private Map<String, String> getSingcleContentsMap(Document doc) {

        Map<String, String> contentsHashMap = new HashMap<>();
        //商品title
        String titleStr = getTitile(doc);
        contentsHashMap.put("productName", titleStr);

        proceed++;
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " total{0} , get{1}", new Object[]{totalItemCount, proceed});
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " title {0} ", titleStr);

        //要約
        String summaryStr = getSummary(doc);
        contentsHashMap.put("summary", summaryStr);

        //拡大画像
        String pictureStr = getPicture(doc);
        contentsHashMap.put("pictureUrl", pictureStr);

        //テーブル系のデータ
        StringBuilder tableStr = getContentsFromTableTag(doc);
        convertContentsDetail(contentsHashMap, tableStr.toString());

        return contentsHashMap;
    }

    /**
     * 商品名の取得
     *
     * @param doc docオブジェクト
     * @return 商品名の取得
     */
    private String getTitile(Document doc) {
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

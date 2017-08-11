/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.app.ConfigManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 複数商品(主にリンク)の管理
 *
 * @author Norio
 */
public class LinkScrapingService {

    /**
     * 一覧画面の最初の画面(1ページ目)
     */
    private final String firstPageLink;

    /**
     * 総ページ状況
     */
    private Integer totalPageCount;

    /**
     * ただいまのページ状況
     */
    private int pageCount;

    public LinkScrapingService(String firstPageLink) {
        this.firstPageLink = firstPageLink;
    }

    /**
     * 実際のリンクプロセス取得
     *
     * @return リンク
     * @throws Exception
     */
    public List<String> getLinkStart() throws Exception {

        Integer totalPageCountTmp = totalPageNum();
        if (totalPageCountTmp == null || totalPageCountTmp <= 0) {
            throw new IllegalStateException();
        }
        setTotalPageCount(totalPageCountTmp);
        List<String> links = getLinks();
        return links;
    }

    /**
     * ページ数を取得
     *
     * @return トータルページ数
     */
    private Integer totalPageNum() {
        Document doc = getDocument(firstPageLink);
        String pagerText = doc.select("li.terminal a").first().attr("href");

        Pattern p = Pattern.compile(".*/page=(\\d*)/");
        Matcher m = p.matcher(pagerText);

        Integer totalPageNum = null;

        if (m.find() && m.group(1) != null) {
            totalPageNum = Integer.parseInt(m.group(1));
        }

        return totalPageNum;
    }

    /**
     * 総ページ数の取得
     *
     * @param totalPageCountTmp 総ページタイトル
     */
    private void setTotalPageCount(Integer totalPageCountTmp) {
        if (ConfigManager.IS_CONTENTS_TEST) {
            this.totalPageCount = ConfigManager.TEST_PAGE_COUNT;
        } else {
            this.totalPageCount = totalPageCountTmp;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "totalPageCount {0}", this.totalPageCount);
    }

    /**
     * リンクからdocmentオブジェクトを返す
     *
     * @param itemsLinkUrl 一覧ページのURL
     * @return リンクのドキュメントオブジェクト
     */
    private Document getDocument(String itemsLinkUrl) {
        URL url = null;
        Document doc = null;
        try {
            url = new URL(itemsLinkUrl);
            doc = Jsoup.parse(url, 3000);
        } catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    /**
     * ページャーを含んだURLを返す
     *
     * @return 一覧画面のURL
     */
    private String getItemsUrl() {
        return String.format("%s/page=%d/", this.firstPageLink, this.pageCount);
    }

    /**
     * 一覧ページのリンクを取得する
     *
     * @return リンク集
     */
    private List<String> getLinks() {
        List<String> itemLinks = new ArrayList<>();
        for (int i = 1; i <= totalPageCount; i++) {
            this.pageCount = i;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "page No {0}", i);
            String itemsLinkUrl = getItemsUrl();
            List<String> itemsLinksTmp = getItemLinks(itemsLinkUrl);
            itemLinks.addAll(itemsLinksTmp);
        }
        return itemLinks;
    }

    /**
     * 単一ページのリンクを取得する
     *
     * @param itemsLinkUrl 1ページのリンク
     * @return リンク集
     */
    private List<String> getItemLinks(String itemsLinkUrl) {
        List<String> itemsLinksTmp = new ArrayList<>();
        Document doc = getDocument(itemsLinkUrl);
        Elements eles = doc.getElementsByAttributeValueMatching("href", Pattern.compile("^.*detail/=/cid.*$"));

        for (Element ele : eles) {
            String itemLink = ele.getElementsByAttribute("href").attr("href");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "links {0}", itemLink);
            itemsLinksTmp.add(itemLink);
        }
        return itemsLinksTmp;
    }

}

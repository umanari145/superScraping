/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 女優リンクの取得
 *
 * @author Norio
 */
public class ActressLinkScraperService {

    @Getter
    @Setter
    private Integer field_count;

    @Getter
    @Setter
    private String linkUrl;
    
    
    @Getter
    @Setter
    private boolean isDebug;

    public ActressLinkScraperService(String linkUrl) {
        this.linkUrl = linkUrl;
        this.isDebug = true;
    }

    /**
     * 女優リンクの取得
     * 
     * @return 女優リンク
     */
    public List<String> getActressLinks() {
        List<String> availableInitialList = getAvailableInitial();
        List<String> actressList = new ArrayList<>();
        
        for(String initial:availableInitialList) {
            actressList.addAll(getActressLinkByInitial(initial));
        }
        
        return actressList;
    }

    /**
     * 有効なイニシャルの作成
     *
     * @return 有効なイニシャルリスト
     */
    public List<String> getAvailableInitial() {
        List<String> initList = makeInitial();
        List<String> excludeList = getExcludeInitial();
        List<String> availableInitialList = initList.stream().filter(initial -> !excludeList.contains(initial))
                .collect(Collectors.toList());
        return availableInitialList;
    }

    /**
     * イニシャルの数列を作成
     *
     * @return イニシャルのリスト
     */
    private List<String> makeInitial() {
        String[] vowelArr = {"a", "i", "u", "e", "o"};
        String[] consonantArr = {"", "k", "s", "t", "n", "h", "m", "y", "r", "w"};
        List<String> initialList = new ArrayList<>();
        for (String consonant : consonantArr) {
            for (String vowel : vowelArr) {
                String initial = consonant + vowel;
                initialList.add(initial);
            }
        }
        return initialList;
    }

    /**
     * 除外イニシャルの取得
     *
     * @return 除外イニシャルリスト
     */
    private List<String> getExcludeInitial() {
        List<String> excludeList = new ArrayList<>();
        String[] excludeArr = {"yi", "ye", "wi", "wu", "we", "wo"};
        excludeList.addAll(Arrays.asList(excludeArr));
        return excludeList;
    }

    /**
     * イニシャルから女優URLの取得
     * 
     * @param initial イニシャル
     * @return リンク
     */
    private List<String> getActressLinkByInitial(String initial) {
        List<String> actressLinkList = new ArrayList<>();
        Integer pageLoopCnt = 0;
        String initialUrl = getActressUrl(initial, pageLoopCnt);
        pageLoopCnt = this.getActressCountByInitial(initialUrl);
        if (pageLoopCnt > 0) {
            for (Integer i = 1; i <= pageLoopCnt; i++) {
                String actressUrl = getActressUrl(initial, i);
                if(this.isDebug && i > 100 ) break;
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "actressURL {0}", actressUrl);
                actressLinkList.add(actressUrl);
            }
        }
        return actressLinkList;
    }

    /**
     * 女優数を取得する
     *
     * @param initialUrl　イニシャルのトップのUrl
     * @return イニシャルとの女優数
     */
    private Integer getActressCountByInitial(String initialUrl) {
        Integer actressPageCount = 0;
        try {
            URL url = new URL(initialUrl);
            Document doc = Jsoup.parse(url, 3000);
            String tmpData = doc.getElementsByClass("list-boxcaptside").first().select("p").first().text();
            String regex = ".*?全(.*?)ページ中.*?";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(tmpData);
            //リンクデータを取得する
            if (m.find() && m.group(1) != null) {
                actressPageCount = Integer.parseInt(m.group(1));
            }
        } catch (IOException ex) {
            Logger.getLogger(ActressLinkScraperService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actressPageCount;
    }

    /**
     * 女優URLを取得
     *
     * @param initial イニシャル
     * @return URL
     */
    private String getActressUrl(String initial, Integer pageNo) {

        if (pageNo == 0) {
            return String.format("%s%s%s", linkUrl, "keyword=", initial);
        } else {
            return String.format("%s%s%s/page=%s/", linkUrl, "keyword=", initial, pageNo.toString());
        }
    }

}

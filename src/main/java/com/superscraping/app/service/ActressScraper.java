/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.service;

import com.superscraping.app.ConfigManager;
import com.superscraping.app.ScraperImpl;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class ActressScraper implements ScraperImpl {

    @Getter
    @Setter
    private Integer field_count;

    /**
     * 
     * 
     * @param linkUrl スタートのURL
     * @param i 不要だが別メソッドで作ってしまったので仕方なく・・
     * @return 
     */
    @Override
    public List<Map<String, String>> scarapingContents(String linkUrl,int i) {
        String[] vowelArr = {"a", "i", "u", "e", "o"};
        String[] consonantArr = {"", "k", "s", "t", "n", "h", "m", "y", "r", "w"};

        List<Map<String, String>> totalActressList = new ArrayList<>();
        //文字を組み合わせる
        for (String consonant : consonantArr) {
            for (String vowel : vowelArr) {
                String initial = consonant + vowel;
                //これらのイニシャルは処理を行わない
                List<String> excludeList = new ArrayList<>();
                String[] excludeArr = {"yi", "ye", "wi", "wu", "we", "wo"};
                excludeList.addAll(Arrays.asList(excludeArr));

                if (excludeList.indexOf(initial) < 0) {
                    List<Map<String, String>> actressList = this.getActressListByInitial(linkUrl, initial);
                    if (actressList.size() > 0) {
                        totalActressList.addAll(actressList);
                    }
                }
            }
            if (ConfigManager.IS_ACTRESS_CONTENTS_TEST == true) {
                break;
            }
        }
        return totalActressList;
    }

    private List<Map<String, String>> getActressListByInitial(String linkUrl, String initial) {
        List<Map<String, String>> actressList = new ArrayList<>();
        //Urlを作る
        String initialUrl = linkUrl + "keyword=" + initial;
        int pageLoopCnt = 0;
        pageLoopCnt = this.getActressCountByGroupInitial(initialUrl);
        if (pageLoopCnt > 0) {
             field_count = 0;
            //サイトにアクセスし、女優データを取る
            for (Integer i = 1; i <= pageLoopCnt; i++) {
                String initialUrl2 = initialUrl + "/page=" + i.toString() + "/";
                List<Map<String, String>> actressListGroupBuInitial = this.getActressMap(initialUrl2, initial);
                if (actressListGroupBuInitial != null) {
                    actressList.addAll(actressListGroupBuInitial);
                }
            }
        }
        return actressList;
    }

    private Integer getActressCountByGroupInitial(String initialUrl) {
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
            Logger.getLogger(ActressScraper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actressPageCount;
    }

    private List<Map<String, String>> getActressMap(String initialUrl, String initial) {
        List<Map<String, String>> actressList = new ArrayList<>();
        try {
            URL url = new URL(initialUrl);
            Document doc = Jsoup.parse(url, 3000);
            if (doc == null) {
                return null;
            }

            Element ul = doc.getElementsByClass("act-box-100").first();
            if (ul == null) {
                return null;
            }
            
            Elements lis = ul.select("li");
            for (Element li : lis) {
                Map<String, String> map = this.getActressMap(li, initial);
                if (map != null) {
                    actressList.add(map);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ActressScraper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return actressList;
    }

    private Map<String, String> getActressMap(Element ul, String initial) {
        Map<String, String> map = new HashMap<>();
        if (ul.text() != null && ul.text().length() > 0) {
            //リンク属性のhrefプロパティを取り出す
            String linkData = ul.getElementsByTag("a").first().attr("href");
            String regex = "id=(\\d*)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(linkData);
            //リンクデータを取得する
            if (m.find()) {
                field_count++;
                String girlsId = m.group(1);
                if (girlsId != null && girlsId.length() > 0) {
                    String name = ul.select("img").first().attr("alt");
                    map = this.makeActressMap(girlsId, name , initial);
                }
            }
        }
        return map;
    }

    private Map<String, String> makeActressMap(String girlsId, String name, String initial) {
        Map<String, String> map = new HashMap<>();
        map.put("dmm_girls_id", girlsId);
        map.put("initial", initial);
        map.put("name", name);
        map.put("initial_order", field_count.toString());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " name {0} ", name);
        return map;
    }
}

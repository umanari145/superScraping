/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.link;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Norio
 */
public class ActressScraper implements ScraperImpl {

    @Override
    public List<Map<String, String>> scarapingContents(String linkUrl) {
        String[] vowelArr = {"a", "i", "u", "e", "o"};
        String[] consonantArr = {"", "k", "s", "t", "n", "h", "m", "y", "r", "w"};

        List<Map<String, String>> actressList = new ArrayList<>();
        //文字を組み合わせる
        for (String consonant : consonantArr) {

            for (String vowel : vowelArr) {

                String initial = consonant + vowel;
                //これらのイニシャルは処理を行わない
                List<String> excludeList = new ArrayList<>();
                String[] excludeArr = {"yi", "ye", "wi", "wu", "we", "wo"};
                excludeList.addAll(Arrays.asList(excludeArr));

                if (excludeList.indexOf(initial) < 0) {
                    //Urlを作る
                    String initialUrl = linkUrl + "keyword=" + initial;
                    //サイトにアクセスし、女優データを取る
                    List<Map<String, String>> actressListGroupBuInitial = this.getActressMap(initialUrl, initial);
                    if (actressListGroupBuInitial != null) {
                        actressList.addAll(actressListGroupBuInitial);
                    }
                }
            }
            if (ConfigManager.IS_ACTRESS_CONTENTS_TEST == true) {
                break;
            }
        }
        return actressList;
    }

    private List<Map<String, String>> getActressMap(String initialUrl, String initial) {
        List<Map<String, String>> actressList = new ArrayList<>();
        try {
            URL url = new URL(initialUrl);
            Document doc = Jsoup.parse(url, 3000);

            if (doc == null) {
                return null;
            }

            Element table = doc.select("table.actress-all").first();

            if (table == null) {
                return null;
            }

            Elements elTd = table.select("td");
            Integer count = 0;
            for (Element el : elTd) {
                if (el.text() != null && el.text().length() > 0) {
                    count++;
                    Map<String, String> map = new HashMap<>();
                    map.put("initial", initial);
                    map.put("name", el.text());
                    map.put("initial_order", count.toString());
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, " name {0} ", el.text());
                    actressList.add(map);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ActressScraper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return actressList;
    }

}

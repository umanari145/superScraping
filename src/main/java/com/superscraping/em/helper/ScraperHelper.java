/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Norio
 */
public class ScraperHelper {
    
    /**
     * リンクからdocmentオブジェクトを返す
     * 
     * @param url
     * @return リンクのドキュメントオブジェクト
     */
    public static Document getDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.parse(new URL(url),3000);
        } catch (MalformedURLException ex) {
            Logger.getLogger(url).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger("ScraperHelper").log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
    /**
     * 文字列を取り出す
     * 
     * @param regex　正規表現
     * @param str 対象文字列
     * @return 抽出文字
     */
    public static String getExtract(String regex, String str){
         Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find() && m.group(1) != null) {
            return m.group(1);
        }
        return null;
    }
}

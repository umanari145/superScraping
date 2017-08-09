/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 *  ItemのLinkのオブジェクト
 * @author Norio
 */
public class ItemLink {
        
    /**
     * Link
     */
    private final String ItemLink;
    
    /**
     * インスタンス
     * @param ItemLink 
     */
    public ItemLink(String ItemLink) {
        this.ItemLink = ItemLink;
    }
   
    /**
     * リンクからdocmentオブジェクトを返す
     * @return リンクのドキュメントオブジェクト
     */
    public Document getDocument() {
        URL url = null;
        Document doc = null;
        try {
            url = new URL(this.ItemLink);
            doc = Jsoup.parse(url, 3000);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ItemLink.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ItemLink.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

}

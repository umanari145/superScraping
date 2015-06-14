/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.page;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
public class DmmContentsGetter implements GetContentsDataAvailable{
  
    @Override
    public List<Map<String,String>> getHtmlContents(List<String> contentsLinkListAll){
        
        List<Map<String,String>> contentsMap = new ArrayList<>();
        for(String link:contentsLinkListAll){
            String contentsDetail = getSingleContentsDetail(link);
            Map<String,String> tmpMap = convertContentsDetail(contentsDetail);
            contentsMap.add(tmpMap);
        }
        return contentsMap;
    }
    
    public String getSingleContentsDetail(String contentsUrl) {
        StringBuilder contentsDetail=null;
        try {
            contentsDetail = new StringBuilder();
            URL url = new URL(contentsUrl);
            Document doc = Jsoup.parse(url, 3000);
            contentsDetail = getHTMLAttribute(doc);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(DmmContentsGetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DmmContentsGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contentsDetail.toString();
    }

    public StringBuilder getHTMLAttribute(Document doc) {
        StringBuilder contentsDetail = new StringBuilder();
        
        StringBuilder titleStr = getTitile(doc);
        contentsDetail.append(titleStr);
 
        StringBuilder summaryStr = getSummary(doc);
        contentsDetail.append(summaryStr);
       
        StringBuilder tableStr = getContentsFromTableTag(doc);
        contentsDetail.append(tableStr);
        
        return contentsDetail;
    }
    
    public StringBuilder getTitile(Document doc){
        StringBuilder titleStr= new StringBuilder();
        Element title = doc.getElementById("title");
        titleStr.append("productName").append("：\n");
        titleStr.append(title.text()).append("\n");
        return titleStr;
    }
    
    public StringBuilder getSummary(Document doc){
        StringBuilder summaryStr= new StringBuilder();
        Elements summaryElement = doc.getElementsByClass("lh4");
        summaryStr.append("summary").append("：\n");
        summaryStr.append(summaryElement.text()).append("\n");
        return summaryStr;
    }
    
    public StringBuilder getContentsFromTableTag(Document doc){
        StringBuilder tableStr = new StringBuilder();
        Element table = doc.select("table[class=mg-b20]").first();
        Elements elTd = table.select("td");
        for (Element el : elTd) {
            tableStr.append(el.text()).append("\n");
        }
        return tableStr;
    }
    

    public Map<String, String> convertContentsDetail(String contentsDetail) {
        contentsDetail = contentsDetail.replaceAll("：\n", ":");
        String[] contentsColumn = contentsDetail.split("\n");
        
        Map<String, String> contentsHashMap = new HashMap<>();
        for (String singleContentsColumn : contentsColumn) {
            String[] contentsDetailData = singleContentsColumn.split(":");
            contentsHashMap.put(contentsDetailData[0], contentsDetailData[1]);
        }
        return contentsHashMap;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.link;

import com.superscraping.app.ConfigManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class DmmLinkGetter implements GetLinkDataAvailable{
    
    @Override
    public List<String> getContentsFromHtml(String linkUrl){        
        List<String> contentsLinkList = new ArrayList<>();
        try{      
            Document document = Jsoup.connect(linkUrl).get();
            Elements eles = document.getElementsByAttributeValueMatching("href", Pattern.compile("^.*detail/=/cid.*$"));
            int testloopCnt = 0;
            for (Iterator<Element> iterator = eles.iterator(); iterator.hasNext();) {
                testloopCnt++;
                Element contentsLinkEle = iterator.next();
                Attributes contentsLinkAttr = contentsLinkEle.attributes();
                String link = contentsLinkAttr.get("href");
                contentsLinkList.add(link);
                
                if ( ConfigManager.CONTENTS_TEST_FLG && testloopCnt > 1) {
                    break;
                }
            }
            
        }catch(IOException e){
            e.getMessage();
        }
        
        return contentsLinkList;
    }
}

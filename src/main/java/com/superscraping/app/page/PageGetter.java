/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.page;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Norio
 */
public class PageGetter {
 
    public PageGetter(){}
    
    public List<Map<String,String>> getContentes(List<String> contentsLinkListAll){
        GetContentsDataAvailable getContentsData = new DmmContentsGetter();
        List<Map<String,String>> contentsMap = getContentsData.getHtmlContents(contentsLinkListAll);
        return contentsMap;
    }
}

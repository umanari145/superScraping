/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
public class LinkGetter {
    
    @Getter @Setter
    public List<String> contentsLinkListAll;
    
    public LinkGetter(){
        contentsLinkListAll = new ArrayList<>();
    }

    //1ページあたりのリンクの集計
    public List<String> getContentsLink(String linkUrl) {
        
        GetLinkDataAvailable getLinkData = new DmmLinkGetter();
        List<String> contentsLinkList = getLinkData.getContentsFromHtml(linkUrl);
        contentsLinkListAll.addAll(contentsLinkList);
        
        return contentsLinkListAll;
    }
    
    
}

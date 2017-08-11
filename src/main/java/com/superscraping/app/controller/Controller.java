/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app.controller;

import com.superscraping.app.ConfigManager;
import com.superscraping.app.RegistImpl;
import com.superscraping.app.ScraperImpl;
import com.superscraping.app.service.ActressScraper;
import com.superscraping.app.service.ItemRegistService;
import com.superscraping.app.service.ItemScrapingService;
import com.superscraping.app.service.LinkScrapingService;
import com.superscraping.entity.DmmItem;
import com.superscraping.entity.DmmItems;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
public class Controller {

    @Getter
    @Setter
    private String siteUrl;

    private RegistImpl actressRegister;
 
    private ScraperImpl actressScraper;

    private ConfigManager configManager;

    private final LinkScrapingService dmmItemsService;

    private ItemScrapingService dmmItemService;
   
    private final ItemRegistService itemRegistService;
    
    public static void main(String[] args) {
        //Weldのコンテナを起動
        Controller controller = new Controller();
        controller.start();
    }

    public Controller() {
        actressScraper = new ActressScraper();
        dmmItemsService = new LinkScrapingService(configManager.SITE_URL);
        itemRegistService = new ItemRegistService();
        //actressRegister = new ActressRegister();

    }

    public void start() {
        //スタート
        DmmItems dmmItems = doItemScraping();
        itemRegistService.registItems(dmmItems);
    }  

    public DmmItems doItemScraping() {
        //リンク一覧からコンテンツを取得
        List<String> links;
        DmmItems dmmItems = new DmmItems();
        
        try {
            links = dmmItemsService.getLinkStart();
            for (String link : links) {
                if (ConfigManager.IS_SINGLE_ITEM_TEST && dmmItems.size() > ConfigManager.TEST_ITEM_COUNT) break;
                DmmItem dmmItem = getDmmItem(link);
                dmmItems.addDmmItems(dmmItem);
                Logger.getLogger(Controller.class.getName()).log(Level.INFO, "item count {0}", dmmItems.size());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dmmItems;
    }

    /**
     * 1商品データを取得する
     * 
     * @param link ページリンク
     * @return DmmItem
     */
    private DmmItem getDmmItem(String link) {
        Logger.getLogger(Controller.class.getName()).log(Level.INFO, "link {0}", link);
        dmmItemService = new ItemScrapingService(link);
        DmmItem dmmItem = dmmItemService.getDmmItem();
        Logger.getLogger(Controller.class.getName()).log(Level.INFO, "item {0}", dmmItem.getProductName());
        return dmmItem;        
    }

    public void actressAction() {
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = actressScraper.scarapingContents(siteUrl, 1);
        //リンクを登録
        actressRegister.registContents(contensMap);
    }


}

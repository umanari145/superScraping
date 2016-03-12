/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.app.link.ActressScraper;
import com.superscraping.app.link.DmmScraper;
import com.superscraping.em.ActressRegister;
import com.superscraping.em.RegistController;
import com.superscraping.entity.DmmProduct;
import java.util.List;
import java.util.Map;
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

    @Getter
    @Setter
    private boolean testFlg = false;

    private RegistImpl itemRegister;

    private RegistImpl actressRegister;

    private ScraperImpl dmmScraper;

    private ScraperImpl actressScraper;

    private ConfigManager configManager;

    public static void main(String[] args) {
        //Weldのコンテナを起動
        Controller controller = new Controller();
        controller.start();
    }

    public Controller() {   
        actressScraper = new ActressScraper();
        dmmScraper = new DmmScraper();
        
        itemRegister = new RegistController();
        actressRegister = new ActressRegister();
        
        configManager = new ConfigManager();
    }

    public void start() {
        //設定の初期化と読込
        init();
        //スタート
        //itemAction();
        actressAction();
        
        //getEntity();
    }

    public void init() {
        this.siteUrl = configManager.ACTRESS_SITE_URL;
    }
    
    public void getEntity(){
        List<DmmProduct> DmmProduct = itemRegister.getEntity();
        System.out.print("aaa");
    }
    
    public void itemAction() {
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = dmmScraper.scarapingContents(siteUrl);
        //リンクを登録
       itemRegister.registContents(contensMap);
    }

    public void actressAction() {
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = actressScraper.scarapingContents(siteUrl);
        //リンクを登録
       actressRegister.registContents(contensMap);
    }
    
}

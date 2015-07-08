/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.app.link.DmmScraper;
import com.superscraping.em.DBRegister;
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

    @Getter
    @Setter
    private RegistImpl register;


    @Getter
    @Setter
    private ScraperImpl scraper;

    @Getter
    @Setter
    private ConfigManager configManager;

    public static void main(String[] args) {
        //スタート
        Controller controller = new Controller();
        controller.start();
    }

    public Controller() {   
        configManager = new ConfigManager();
        //スクレイピングオブジェクトの初期化
        scraper = new DmmScraper();
        //登録オブジェクトの初期化
        register = new DBRegister();
    }

    public void start() {
        //設定の初期化と読込
        init();
        //スタート
        action();
    }

    public void init() {
        this.siteUrl = ConfigManager.SITE_URL;
    }

    public void action() {
       
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = scraper.scarapingContents(siteUrl);
        //リンクを登録
        register.registContents(contensMap);
    }

}

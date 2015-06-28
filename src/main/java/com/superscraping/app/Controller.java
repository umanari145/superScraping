/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.app.link.DmmScraper;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
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
    @Inject
    private RegistImpl register;


    @Getter
    @Setter
    @Inject
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
        List<Map<String, String>> contensMap = this.scraper.scarapingContents(siteUrl);
        //リンクを登録
        register.registContents(contensMap);
    }

}

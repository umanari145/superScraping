/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.app.link.DmmScraper;
import com.superscraping.em.RegistController;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

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

    private RegistImpl register;


    private ScraperImpl scraper;

    private ConfigManager configManager;

    public static void main(String[] args) {
        //Weldのコンテナを起動
        Controller controller = new Controller();
        controller.start();
    }

    public Controller() {   
        scraper = new DmmScraper();
        register = new RegistController();
        configManager = new ConfigManager();
    }

    public void start() {
        //設定の初期化と読込
        init();
        //スタート
        action();
    }

    public void init() {
        this.siteUrl = configManager.SITE_URL;
    }

    public void action() {
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = scraper.scarapingContents(siteUrl);
        //リンクを登録
        register.registContents(contensMap);
    }

}

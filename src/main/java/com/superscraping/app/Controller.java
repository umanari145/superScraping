/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

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

    @Inject
    private RegistImpl register;


    @Inject
    private ScraperImpl scraper;

    @Inject
    private ConfigManager configManager;

    public static void main(String[] args) {
        //Weldのコンテナを起動
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        Controller controller = container.instance().select(Controller.class).get();
        controller.start();
        weld.shutdown();
    }

    public Controller() {   
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

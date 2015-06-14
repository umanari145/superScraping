/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.app;

import com.superscraping.app.link.LinkGetter;
import com.superscraping.app.page.PageGetter;
import com.superscraping.em.ProductDB;
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
    private List<String> jenre;

    @Getter
    @Setter
    private List<String> actress;

    @Getter
    @Setter
    private List<String> maker;

    @Getter
    @Setter
    private boolean testFlg = false;

    @Getter
    @Setter
    private ProductDB productDb;

    @Getter
    @Setter
    private LinkGetter linkGetter;

    @Getter
    @Setter
    private PageGetter pageGetter;

    @Getter
    @Setter
    private ConfigManager configManager;

    public static void main(String[] args) {
        //スタート
        Controller controller = new Controller();
        controller.start();
    }

    public Controller() {
        productDb = new ProductDB();
        linkGetter = new LinkGetter();
        pageGetter = new PageGetter();
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
        //リンクを取得
        List<String> contentsLinkListAll = linkGetter.getContentsLink(siteUrl);
        //リンク一覧からコンテンツを取得
        List<Map<String, String>> contensMap = pageGetter.getContentes(contentsLinkListAll);

        productDb.registEntity(contensMap);
    }

}

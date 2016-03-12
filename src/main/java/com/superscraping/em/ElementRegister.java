/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.entity.Girls;
import com.superscraping.entity.ItemGirls;
import com.superscraping.entity.ItemTags;
import com.superscraping.entity.Tags;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Norio
 */
public class ElementRegister extends DBbase {

    private EntityManager em;

    /**
     * 保存可能なタグのリスト
     */
    private List<String> canRegistTagList = Arrays.asList("女子校生", "人妻","SM", "ロリ系", "コスプレ", "巨乳", "痴漢", "素人", "パンチラ","ナンパ","痴女","");

    /**
     * 商品のタグデータからデータベースのidを取得する
     *
     * @param tagStr
     * @return tagリスト
     */
    public List<Tags> getTagData(String tagStr) {
        List<Tags> tagList = new ArrayList<>();

        String[] tagArr = tagStr.split(" ");
        for (String tag : tagArr) {
            if( canRegistTag(tag) == true ) {
                Tags tagEntity = this.findTagClass(tag);            
                tagList.add(tagEntity);
            }
        }
        return tagList;
    }

    /**
     * 女優データからデータベースのidを取得する
     *
     * @param girlStr
     * @return girlリスト
     */
    public List<Girls> getGirlsData(String girlStr) {
        List<Girls> girlList = new ArrayList<>();

        if( girlStr.equals("----") || girlStr.equals("▼すべて表示する") ){
            return girlList;
        }
        
        String[] girlsArr = girlStr.split(" ");
        for (String girl : girlsArr) {
            Girls girlsEntity = this.findGirlClass(girl);            
            girlList.add(girlsEntity);
        }
        return girlList;
    }    
    
    /**
     * タグエンティティの取得
     *
     * @param tag タグ文字列
     * @return タグエンティティ
     */
    private Tags findTagClass(String tag) {
        EntityManager em = this.getEm();
        List<Tags> tagList = em.createQuery(" SELECT t FROM Tags t WHERE t.tag = :tags ", Tags.class)
                .setParameter("tags", tag)
                .getResultList();

        Tags tagEntity = null;
        if (tagList.size() > 0) {
            //リストから取得
            tagEntity = tagList.get(0);
        } else {
            //なければ保存
            tagEntity = new Tags();
            tagEntity.setTag(tag);
            this.create(tagEntity);
            this.getFlush();
        }
        return tagEntity;
    }
    
    /**
     * 女優データの保存
     * 
     * @param girl Girlオブジェクト 
     */
    public void registGirl(Girls girl){
        this.create(girl);
    }

    /**
     * 女優エンティティの取得
     *
     * @param girl 女優文字列
     * @return 女優エンティティ
     */
    private Girls findGirlClass(String girl) {
        EntityManager em = this.getEm();
        List<Girls> girlList = em.createQuery(" SELECT g FROM Girls g WHERE g.name = :girls ", Girls.class)
                .setParameter("girls", girl)
                .getResultList();

        Girls girlEntity = null;
        if (girlList.size() > 0) {
            //リストから取得
            girlEntity = girlList.get(0);
        } else {
            //なければ保存
            girlEntity = new Girls();
            girlEntity.setName(girl);
            this.create(girlEntity);
            this.getFlush();
        }
        return girlEntity;
    }    
    
    
    /**
     * 関連テーブル(タグ)の保存
     *
     * @param productId 商品id
     * @param tagList タグリスト
     */
    public void registRelateProductAndTag(Integer productId, List<Tags> tagList) {
        tagList.stream().forEach(tag -> {
            ItemTags itemTags = new ItemTags();
            itemTags.setItemId(productId);
            itemTags.setTagId(tag.getId());
            this.create(itemTags);
        });
    }
    
    /**
     * 関連テーブル(女優)の保存
     *
     * @param productId 商品id
     * @param girlList 女優リスト
     */
    public void registRelateProductAndGirl(Integer productId, List<Girls> girlList) {
        girlList.stream().forEach(girl -> {
            ItemGirls itemGirls = new ItemGirls();
            itemGirls.setItemId(productId);
            itemGirls.setGirlId(girl.getId());
            this.create(itemGirls);
        });
    }

    
    /**
     * タグが該当のものにある場合、保存する。 その判定を行う
     *
     * @param tag タグ
     * @return true(保存できる) /false(保存できない)
     */
    private boolean canRegistTag(String tag) {
        //保存可能なタグのリスト
        return canRegistTagList.indexOf(tag) >= 0;
    }
}

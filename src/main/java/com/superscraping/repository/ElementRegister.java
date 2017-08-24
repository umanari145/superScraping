/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.entity.Actresses;
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
     * @param girlIdList
     * @return girlリスト
     */
    public List<Actresses> getGirlsData(List<String> girlIdList) {
        List<Actresses> girlList = new ArrayList<>();

        if( girlIdList.isEmpty() ){
            return girlList;
        }
      
        for (String girlId : girlIdList) {
            Actresses girlsEntity = this.findGirlClass(girlId); 
            if( girlsEntity != null){
                girlList.add(girlsEntity);
            }
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
    public void registGirl(Actresses girl){
        this.create(girl);
    }

    /**
     * 女優エンティティの取得
     *
     * @param girlId 女優Id
     * @return 女優エンティティ
     */
    private Actresses findGirlClass(String girlId) {
        EntityManager em = this.getEm();
        List<Actresses> girlList = em.createQuery(" SELECT g FROM Girls g WHERE g.dmmGirlId = :girlId ", Actresses.class)
                .setParameter("girlId", Integer.parseInt(girlId))
                .getResultList();

        Actresses girlEntity = null;
        if (girlList.size() > 0) {
            //リストから取得
            girlEntity = girlList.get(0);
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
    public void registRelateProductAndGirl(Integer productId, List<Actresses> girlList) {
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

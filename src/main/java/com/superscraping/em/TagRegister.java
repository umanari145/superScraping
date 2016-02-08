/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.entity.ItemTags;
import com.superscraping.entity.Tags;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Norio
 */
@Stateless
public class TagRegister extends DBbase{
        
    
    private EntityManager em;
    
    /**
     * 商品のタグデータからタグデータベースのidを取得する
     *
     * @param productTagStr
     * @return タグエンティティリスト
     */
    public List<Tags> getTagData(String productTagStr) {
        List<Tags> tagList = new ArrayList<>();

        String[] tagArr = productTagStr.split(" ");
        for (String tagStr : tagArr) {
            Tags tagEntity = this.findTagClass(tagStr);
            tagList.add(tagEntity);
        }
        return tagList;
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
            tagEntity = tagList.get(0);
        }
        return tagEntity;
    }

    /**
     * 関連テーブルの保存
     * 
     * @param productId 商品id
     * @param tagList タグのリスト
     */
    void registRelateProductAndTag(Integer productId, List<Tags> tagList) {
            tagList.stream().forEach(tag->{
                ItemTags itemTags = new ItemTags();
                itemTags.setItemId(productId);
                itemTags.setTagId(tag.getId());
                this.create(itemTags);
            });
    }
}

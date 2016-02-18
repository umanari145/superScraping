/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.entity.ItemTags;
import com.superscraping.entity.Tags;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Norio
 */
@Stateless
public class TagRegister extends DBbase {

    private EntityManager em;

    /**
     * 保存可能なタグのリスト
     */
    private List<String> canRegistTagList = Arrays.asList("女子校生", "人妻","SM", "ロリ系", "コスプレ", "巨乳", "痴漢", "素人", "パンチラ","ナンパ","痴女","");

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
            if( canRegistTag(tagStr) == true ) {
                Tags tagEntity = this.findTagClass(tagStr);            
                tagList.add(tagEntity);
            }
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
            //リストから取得
            tagEntity = tagList.get(0);
        } else {
            //なければ保存
            tagEntity = new Tags();
            tagEntity.setTag(tag);
            this.create(tagEntity);
        }
        return tagEntity;
    }

    /**
     * 関連テーブルの保存
     *
     * @param productId 商品id
     * @param tagList タグのリスト
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

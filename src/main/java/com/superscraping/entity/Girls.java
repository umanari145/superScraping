/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 女優のファーストコレクション
 * @author Norio
 */
public class Girls {
       
    /**
     * Girlのリスト
     */
    private final List<Girl> Girls;
    
    public Girls() {
        Girls = new ArrayList<>();
    }
    
    public void addGirls(Girl girl) {
        Girls.add(girl);
    }
    
}

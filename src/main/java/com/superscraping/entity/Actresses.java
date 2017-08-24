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
public class Actresses {
       
    /**
     * Actressのリスト
     */
    private final List<Actress> Actresses;
    
    public Actresses() {
        Actresses = new ArrayList<>();
    }
    
    public void addActresses(Actress actress) {
        Actresses.add(actress);
    }
    
}

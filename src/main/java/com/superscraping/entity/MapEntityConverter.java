/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.util.Map;

/**
 *
 * @author Norio
 */

public interface MapEntityConverter {

    public BaseProduct mapToEntity(Map<String, String> contentsMap);
        
}
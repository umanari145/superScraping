/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em.helper;

import com.superscraping.entity.BaseEntity;
import java.util.Map;

/**
 *
 * @author Norio
 */

public interface MapEntityConverter {

    public BaseEntity mapToEntity(Map<String, String> contentsMap);
        
}
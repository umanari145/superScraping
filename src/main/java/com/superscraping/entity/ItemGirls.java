/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
@Entity
@Table(name = "item_girls")
public class ItemGirls extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Basic(optional = false)
    @NotNull
    @Column(name = "item_id")
    @Getter
    @Setter
    private int itemId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "girl_id")
    @Getter
    @Setter
    private int girlId; 
    
}

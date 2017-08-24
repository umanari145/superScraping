/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *  Girlのエンティティ
 * @author Norio
 */
@Entity
@Table(name = "actress")
public class Actress extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;    
       
    @Column(name = "dmm_actress_id")
    @Getter
    @Setter
    private Integer dmmActressId;
        
    @Size(max = 255)
    @Column(name = "initial")
    @Getter
    @Setter
    private String initial; 
    
    @Size(max = 255)
    @Column(name = "name")
    @Getter
    @Setter
    private String name;   

    @Column(name = "initial_order")
    @Getter
    @Setter
    private Integer initialOrder;
    
    public Actress() {
        
    }
    
    public Actress(Integer dmmActresslId) {
        this.dmmActressId = dmmActresslId;
    }
}

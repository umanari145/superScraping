/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
@MappedSuperclass
public abstract class BaseEntity {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter 
    @Setter    
    private Date created;
    
    @Column(name = "modified")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter 
    @Setter
    private Date modified;
    
    @Override
    public int hashCode() {
        int hash = 0;
        Integer id = this.getId();
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        return true;
    }



}

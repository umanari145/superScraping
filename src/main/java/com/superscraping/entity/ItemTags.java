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
@Table(name = "item_tags")
public class ItemTags extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
 
    @Basic(optional = false)
    @NotNull
    @Column(name = "item_id")
    @Getter
    @Setter
    private int itemId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "tag_id")
    @Getter
    @Setter
    private int tagId;

    public ItemTags() {
    }

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

    @Override
    public String toString() {
       Integer id = this.getId();
        return "com.superscraping.entity.ItemTags[ id=" + id + " ]";
    }
    
}

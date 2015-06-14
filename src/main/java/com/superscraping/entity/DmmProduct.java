/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
@Entity
@Table(name = "product")
public class DmmProduct extends BaseProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;
   
    @Column(name = "releaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter    
    private Date releaseDate;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "actress")
    @Getter @Setter    
    private String actress;
    
    @Size(max = 255)
    @Column(name = "maker")
    @Getter @Setter    
    private String maker;
    
    @Size(max = 255)
    @Column(name = "label")
    @Getter @Setter    
    private String label;
    
    @Size(max = 100)
    @Column(name = "productCode")
    @Getter @Setter    
    private String productCode;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "genre")
    @Getter @Setter    
    private String genre;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "summary")
    @Getter @Setter    
    private String summary;    
    
    
    public DmmProduct() {
    }

    public DmmProduct(Integer id) {
        this.setId(id);
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DmmProduct)) {
            return false;
        }
        DmmProduct other = (DmmProduct) object;
        Integer id = this.getId();
        if ((id == null && other.getId() != null) || (id != null && !id.equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Integer id = this.getId();
        return "com.superscraping.entity.Product[ id=" + id + " ]";
    }
    
}

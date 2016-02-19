/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.entity.BaseEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
@Stateless
public class DBbase {
    
    private EntityManagerFactory fac;
    
    @Getter 
    @Setter
    private EntityManager em;
    
    private EntityTransaction tx;

    public DBbase() {
        fac = Persistence.createEntityManagerFactory("com_SuperScraping_jar_1.0-SNAPSHOTPU");
        em = fac.createEntityManager();
        tx = em.getTransaction();
    }

    public void create(BaseEntity baseEntity) {
        tx.begin();
        em.persist(baseEntity);
        tx.commit();
    }
    
    public BaseEntity find(BaseEntity baseEntity,Object id){
        return em.find(BaseEntity.class, id);
    }
}

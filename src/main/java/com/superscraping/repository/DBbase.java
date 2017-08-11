/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.repository;

import com.superscraping.entity.BaseEntity;
import java.util.Date;
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
public class DBbase {
    
    private EntityManagerFactory fac;
    
    @Getter 
    @Setter
    private EntityManager em;
    
    private EntityTransaction tx;

    protected DBbase() {
        fac = Persistence.createEntityManagerFactory("com_SuperScraping_jar_1.0-SNAPSHOTPU");
        em = fac.createEntityManager();
        tx = em.getTransaction();
    }

    public void startTransaction(){
        tx.begin();        
    }
    
    public void getFlush(){
        em.flush();
    }
    
    protected void create(BaseEntity baseEntity) {
        baseEntity.setCreated(new Date());
        baseEntity.setModified(new Date());        
        em.persist(baseEntity);
    }
    
    public void transactionCommit(){
        tx.commit();
    }
    
    protected BaseEntity find(BaseEntity baseEntity,Object id){
        return em.find(BaseEntity.class, id);
    }
}

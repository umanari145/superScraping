/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.app.RegistImpl;
import com.superscraping.entity.BaseProduct;
import com.superscraping.em.helper.DmmConverter;
import com.superscraping.entity.DmmProduct;
import com.superscraping.em.helper.MapEntityConverter;
import java.util.List;
import java.util.Map;
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
public class DBRegister implements RegistImpl{

    private EntityManagerFactory fac;
    private EntityManager em;
    private EntityTransaction tx;

    @Getter
    @Setter
    private MapEntityConverter mapEntityConverter;
    
    public DBRegister() {
        fac = Persistence.createEntityManagerFactory("com_SuperScraping_jar_1.0-SNAPSHOTPU");
        em = fac.createEntityManager();
        tx = em.getTransaction();
        
        mapEntityConverter = new DmmConverter();
    }

    public void create(BaseProduct product) {
        tx.begin();
        em.persist(product);
        tx.commit();
    }

    public void find(Integer id) {
        em.find(DmmProduct.class, id);
    }

    public List<DmmProduct> getAll() {
        return em.createQuery(" SELECT p FROM Product p ", DmmProduct.class).getResultList();
    }

    @Override
    public void registContents(List<Map<String, String>> contentDetail) {
        contentDetail.stream().forEach(contents -> {    
            BaseProduct product = mapEntityConverter.mapToEntity(contents);
            this.create(product);
        });
    }
}

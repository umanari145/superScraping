/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.em;

import com.superscraping.app.RegistImpl;
import com.superscraping.entity.DmmProduct;
import com.superscraping.entity.Girls;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Norio
 */
public class ActressRegister implements RegistImpl {

    @Getter
    @Setter
    private ElementRegister elementRegister;

    public ActressRegister() {
        elementRegister = new ElementRegister();
    }
    
    @Override
    public void registContents(List<Map<String, String>> contentDetail) {
        elementRegister.startTransaction();

        contentDetail.stream().forEach(contents->{
                Girls girl = new Girls();
                if( contents.get("dmm_girls_id") != null && contents.get("dmm_girls_id").length() >0 ) {
                    girl.setDmmGirlId(Integer.parseInt(contents.get("dmm_girls_id")));
                    girl.setInitial(contents.get("initial"));
                    girl.setName(contents.get("name"));
                    girl.setInitialOrder(Integer.parseInt(contents.get("initial_order")));
                    elementRegister.registGirl(girl);
                }
        });
        elementRegister.transactionCommit();
        
    }

    @Override
    public List<DmmProduct> getEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

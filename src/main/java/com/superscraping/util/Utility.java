/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superscraping.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Norio
 */
public class Utility {

    public static Date strToDate(String textDate) {
        Date convDate = null;
        try {
            convDate = DateFormat.getDateInstance().parse(textDate);
        } catch (ParseException ex) {

        }
        return convDate;
    }

}

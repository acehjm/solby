package me.solby.ifile.csv.service;

import com.opencsv.bean.BeanVerifier;

/**
 * me.solby.ifile.icsv.service
 *
 * @author majhdk
 * @date 2019-08-05
 */
public class ItemVerifier implements BeanVerifier<Item> {

    @Override
    public boolean verifyBean(Item bean) {
        return bean.getSize() < 15;
    }
}

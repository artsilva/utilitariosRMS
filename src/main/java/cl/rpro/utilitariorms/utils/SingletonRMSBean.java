/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 *
 * @author Artsk
 */
@Singleton
public class SingletonRMSBean {

    private Map<String, Object> map;

    @PostConstruct
    private void init() {
        Logger.getLogger(SingletonRMSBean.class.getName()).log(Level.FINEST, "SingletonRMSBean.init()");
        map = new HashMap<>();
    }

    public void clearMap() {
        Logger.getLogger(SingletonRMSBean.class.getName()).log(Level.FINEST, "SingletonRMSBean.clearMap()");
        map.clear();
    }

    public void setProperty(String key, Object value) {
        Logger.getLogger(SingletonRMSBean.class.getName()).log(Level.FINEST, "SingletonRMSBean.setProperty: {0}", key);
        map.put(key, value);
    }

    public Object getProperty(String key) {
        Logger.getLogger(SingletonRMSBean.class.getName()).log(Level.FINEST, "SingletonRMSBean.getProperty: {0}", key);
        return map.get(key);
    }

}

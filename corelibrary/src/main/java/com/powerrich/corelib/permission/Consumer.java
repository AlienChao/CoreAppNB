package com.powerrich.corelib.permission;

import java.util.List;

/**
 * @author AlienChao
 * @date 2018/11/27 16:18
 */
public abstract  class Consumer implements IPermission {




    @Override
    public void noPermission(List<String> denied, boolean quick) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick, List<String> quickStr) {

    }
}

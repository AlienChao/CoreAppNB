package com.powerrich.corelib.permission;

import java.util.List;

/**
 * 权限请求结果回调接口
 */
public interface IPermission {

    /**
     * 有权限  被授予时回调
     *
     * @param granted           返回请求成功的权限
     * @param isAll             是否全部授予了 如果只有一个权限就不用判断了
     */
    void accept(List<String> granted, boolean isAll);

    /**
     * 有权限被拒绝授予时回调
     *
     * @param denied            请求失败的权限组
     * @param quick             是否有某个权限被永久拒绝了
     */
    void noPermission(List<String> denied, boolean quick);


    /**
     * 有权限被拒绝授予时回调
     *
     * @param denied            请求失败的权限组
     * @param quick             是否有某个权限被永久拒绝了
     * @param quickStr          被永久拒绝的权限名
     */
    void noPermission(List<String> denied, boolean quick, List<String> quickStr);


}

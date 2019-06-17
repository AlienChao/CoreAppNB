package com.example.javatestlib;

import java.util.HashMap;

/**
 * @author AlienChao
 * @date 2018/11/28 11:23
 */
public class aa {
    // 日历
    public static final String[] CALENDAR = new String[]{
            "aa",
            "bb"};
    private HashMap<Object,String> stringList;
    public void init(){
        stringList =new HashMap<>();
        stringList.put("aa","11");
        stringList.put("bb","22");
        stringList.put("cc","33");
        stringList.put(CALENDAR,"44");



        System.out.println(stringList.get("bb"));
        System.out.println(stringList.get(CALENDAR));
    }
}

package com.example.javatestlib.reflect;

/**
 * @author AlienChao
 * @date 2018/12/17 08:55
 */
public class Exec {




    public static void run(SuperTest target) {
        System.out.println();

        System.out.println("base class > ");
        run(target, SuperTest.class, "hello");      //1　这里可以正常执行

        System.out.println("obj class  > ");
        run(target, target.getClass(), "hello");    //2　这里可能产生异常
    }


    static void run(Object target, Class cls, String method) {
        try {
            cls.getMethod(method, null).invoke(target, null);
        } catch (Exception x) {
            System.out.println(x);
        }
    }
}

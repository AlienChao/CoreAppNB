package com.example.javatestlib.reflect;

/**
 * @author AlienChao
 * @date 2018/12/17 08:54
 */
public class Test {
    public static void main(String[] args) {
        Exec.run(new SuperTest() {
            @Override
            public void hello(String a) {
                System.out.println(""+a);
            }
        });
    }
}

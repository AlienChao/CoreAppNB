package com.example.javatestlib.reflect2;

/**
 * @author AlienChao
 * @date 2018/12/17 15:20
 */
public class OuterClass {
    public void print(){
        System.out.println("i am Outer class");
    }
    class InnerClass{
        void print2(){
            System.out.println("i am inner class");
        }
    }
}

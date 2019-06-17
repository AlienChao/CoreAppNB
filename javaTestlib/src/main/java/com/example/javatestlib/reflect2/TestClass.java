package com.example.javatestlib.reflect2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author AlienChao
 * @date 2018/12/17 15:20
 */
public class TestClass {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class c = Class.forName("com.example.javatestlib.reflect2.OuterClass");
        //通过方法名获取方法
        Method method = c.getDeclaredMethod("print");
        //调用外部类方法
        method.invoke(c.newInstance());
        //内部类需要使用$分隔
        Class c2 = Class.forName("com.example.javatestlib.reflect2.OuterClass$InnerClass");
        Method method2 = c2.getDeclaredMethod("print2");
        //通过构造函数创建实例,如果没有重写构造方法则不管是不是获取已声明构造方法,结果是一样的
        method2.invoke(c2.getDeclaredConstructors()[0].newInstance(c.newInstance()));
    }
}

package com.example.javatestlib.test001;

/**
 * @author AlienChao
 * @date 2018/12/03 11:10
 */
public class AA extends Eextend implements IInterface {
    @Override
    public void i() {
        System.out.println("AA-i()");
    }

    @Override
    public void e() {
        super.e();
        i();
    }
}

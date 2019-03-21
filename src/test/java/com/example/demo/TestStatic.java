package com.example.demo;

/**
 * Created by caozhen on 2019/3/13
 */
public class TestStatic {


    public static void main(String[] args) {
        System.out.println(Sub.v);
        System.out.println(Sub.a);
    }
}

class Sub1 {
    public static  Sub1 su;
    static {
        System.out.println("123");
    }
     static int v= 123;

}


class Sub extends Sub1{
    static String a ="2";
    static {
        System.out.println("hello world");
    }
}

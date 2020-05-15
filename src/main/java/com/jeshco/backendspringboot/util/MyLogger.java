package com.jeshco.backendspringboot.util;

public class MyLogger {

    public static void showMethodName(String className, String name) {
        System.out.println();
        System.out.println();
        System.out.println(className + ": " + name + " --------------------------------------------------------------------");
    }

}

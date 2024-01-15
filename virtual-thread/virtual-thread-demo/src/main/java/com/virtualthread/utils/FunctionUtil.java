package com.virtualthread.utils;

public class FunctionUtil {

    public static <T> void doTry(ThrowingEffect<T> func) {
        try {
            func.accept();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

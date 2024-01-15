package com.virtualthread.utils;

public interface ThrowingSupplier <T>{

    T get() throws Exception;
}

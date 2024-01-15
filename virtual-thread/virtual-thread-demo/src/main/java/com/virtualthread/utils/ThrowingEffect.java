package com.virtualthread.utils;

public interface ThrowingEffect<T>{

    void accept() throws Exception;
}

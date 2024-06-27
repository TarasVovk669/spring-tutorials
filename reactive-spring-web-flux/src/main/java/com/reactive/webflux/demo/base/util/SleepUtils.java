package com.reactive.webflux.demo.base.util;

public class SleepUtils {

  public static void sleep(int seconds) {
    try {
      Thread.sleep(seconds * 1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

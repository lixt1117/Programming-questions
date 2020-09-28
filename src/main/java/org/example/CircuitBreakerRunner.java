package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: lixiaotian
 * @Date: 2020/9/28 22:11
 * @Description:断线器runner
 */
public class CircuitBreakerRunner {

    public static void run(int i) {
        if (CircuitBreaker.isBreak) {
            fallback(i);
        } else {
            try {
                busi(i);
                CircuitBreaker.push2queue(0);
            } catch (Exception e) {
                CircuitBreaker.push2queue(1);
            }
        }
    }

    private static void busi(int i) throws InterruptedException {
        System.out.println("I'm busi func " + i);
        if (Math.random() * 10 > 5) {
            throw new RuntimeException("I'm exception hhh");
        }
    }

    private static void fallback(int i) {
        System.out.println("I'm fallback " + i);
    }

}

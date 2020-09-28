package org.example;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther: lixiaotian
 * @Date: 2020/9/28 21:38
 * @Description:断线器（单机版本熔断器？）
 */
public class CircuitBreaker {

    public static volatile boolean isBreak = false;

    public static volatile long duanTime = Long.MAX_VALUE;

    private static volatile Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    private static volatile int fallTimes = 0;

    private static final long REBACKTIME = 4000L;


    static {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                if (isBreak) {
                    synchronized (CircuitBreaker.class) {
                        if (System.currentTimeMillis() - duanTime >= REBACKTIME) {
                            isBreak = false;
                            duanTime = Long.MAX_VALUE;
                            queue = new ConcurrentLinkedQueue<Integer>();
                            fallTimes = 0;
                        }
                    }
                }
            }
        };
        timer.schedule(timerTask, 2000, 2000);
        System.out.println("Timer start");
    }

    public static synchronized void push2queue(int isSuccess) {
        if (queue.size() > 50) {
            Integer poll = queue.poll();
            fallTimes -= poll;
        }
        queue.add(isSuccess);
        fallTimes += isSuccess;
        if (fallTimes >= 10) {
            isBreak = true;
            duanTime = System.currentTimeMillis();
        }
    }
}

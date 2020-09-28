package org.example;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

/**
 * @Auther: lixiaotian
 * @Date: 2020/9/28 22:31
 * @Description:
 */
public class CircuitBreakerTest {

    @Test
    public void testBreaker() throws InterruptedException {
        //测试执行线城数量
        int nThreads = 5;
        //模拟并发的总任务
        int TotalRequest = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < TotalRequest; i++) {
            Thread.sleep(100);
            final int finalI = i;
            executorService.execute(new Runnable() {
                public void run() {
                    CircuitBreakerRunner.run(finalI);
                }
            });
        }
        executorService.shutdown();
    }

}

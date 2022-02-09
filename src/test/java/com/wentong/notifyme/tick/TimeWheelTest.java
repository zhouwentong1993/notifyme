package com.wentong.notifyme.tick;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeWheelTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HashedWheelTimer timer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS);
        System.out.println(LocalDateTime.now().format(formatter));
        Timeout task = timer.newTimeout(timeout -> {
            System.out.println(LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);
        task.cancel();
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    void testSingleTask() throws Exception {
        HashedWheelTimer timer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS);
        System.out.println(LocalDateTime.now().format(formatter));
        timer.newTimeout(timeout -> {
            System.out.println(LocalDateTime.now().format(formatter));
        }, 3, TimeUnit.SECONDS);
    }


    /**
     * 多个任务执行是单线程的，所以要么将任务的执行分配到线程池中，要么将任务的执行缩短。
     */
    @Test
    void testTwoTask() throws Exception {
        HashedWheelTimer timer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS);
        System.out.println(LocalDateTime.now().format(formatter));
        timer.newTimeout(timeout -> {
            System.out.println(LocalDateTime.now().format(formatter));
            TimeUnit.SECONDS.sleep(3);
        }, 3, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> {
            System.out.println(LocalDateTime.now().format(formatter));
        }, 4, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    void testWheelSize() {
        int ticksPerWheel = 3;
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        assertEquals(4, normalizedTicksPerWheel);
    }

    @Test
    void testTicksPeerWheel() throws Exception {
        HashedWheelTimer timer = new HashedWheelTimer(10, TimeUnit.MILLISECONDS, 1);
        for (int i = 0; i < 100; i++) {

            Timeout timeout = timer.newTimeout(task -> {
                System.out.println("hello world");
            }, 10, TimeUnit.MINUTES);
            timeout.cancel();
        }
        TimeUnit.SECONDS.sleep(1000);
    }

}

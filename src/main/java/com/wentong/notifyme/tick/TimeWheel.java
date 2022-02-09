package com.wentong.notifyme.tick;

import io.netty.util.HashedWheelTimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeWheel {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS);
        System.out.println(LocalDateTime.now().format(formatter));
        timer.newTimeout(timeout -> System.out.println(LocalDateTime.now().format(formatter)), 2, TimeUnit.SECONDS);
    }

}

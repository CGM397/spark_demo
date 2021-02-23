package nju.cgm.kafkaspring.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Bright Chan
 * @date: 2020/11/19 17:33
 * @description: MsgPool
 */
public class MsgPool {
    private static ExecutorService sendPool = new
            ThreadPoolExecutor(4,
            8,
            0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public static ExecutorService getSendPool() {
        return sendPool;
    }
}

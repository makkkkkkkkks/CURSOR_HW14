package main.java.com.makkkkkkkks.semaphore;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomSemaphore {
    private AtomicInteger permits;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public CustomSemaphore(int permits) {
        this.permits = new AtomicInteger(permits);
    }

    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits.get() == 0) {
                condition.signalAll();
                condition.await();
            }
            permits.decrementAndGet();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            permits.incrementAndGet();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
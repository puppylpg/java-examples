package leetcode.multithread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author puppylpg on 2023/07/23
 */
public class ZeroEvenOdd {
    private int n;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private Lock lock = new ReentrantLock();
    private Condition forZero = lock.newCondition();
    private Condition forOdd = lock.newCondition();
    private Condition forEven = lock.newCondition();
    private AtomicInteger counter = new AtomicInteger(0);
    private volatile boolean printZero = true;

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                while (!printZero) {
                    forZero.await();
                }
                printNumber.accept(0);
                printZero = false;

                if (counter.addAndGet(1) % 2 == 1) {
                    forOdd.signal();
                } else {
                    forEven.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < n / 2; i++) {
            lock.lock();
            try {
                while(printZero || counter.get() % 2 == 1) {
                    forEven.await();
                }

                printNumber.accept(counter.get());
                printZero = true;
                forZero.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 0; i < (n + 1) / 2; i++) {
            lock.lock();
            try {
                while(printZero || counter.get() % 2 == 0) {
                    forOdd.await();
                }

                printNumber.accept(counter.get());
                printZero = true;
                forZero.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}
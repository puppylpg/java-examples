package example.concurrency.synchronization.whataslock.ordinaryobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 事实证明，无论是把this对象作为锁，把非static方法作为锁，还是把static method作为锁，都是把class对象作为锁。
 *
 * @author liuhaibo on 2022/05/18
 */
public class OrdinaryObjectBlended extends LockBase {

    public void lockThis() {
        synchronized (this) {
            work();
        }
    }

    public synchronized void lockMethod() {
        work();
    }

    public void lockNothing() {
        work();
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(9);
        System.out.println("They all lock the same object...");
        lockSameObject(latch);
        latch.await();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:36:48 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:36:49 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:36:50 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:36:51 CST 2022
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:36:52 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:36:53 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:36:54 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:36:55 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:36:56 CST 2022
     */
    public static void lockSameObject(CountDownLatch latch) {
        OrdinaryObjectBlended ordinaryObjectBlended = new OrdinaryObjectBlended();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                ordinaryObjectBlended.lockThis();
                latch.countDown();
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                ordinaryObjectBlended.lockMethod();
                latch.countDown();
            }).start();
        }

        // 显式使用同一个object作为锁（它内部的方法把它用this表示）
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                synchronized (ordinaryObjectBlended) {
                    ordinaryObjectBlended.lockNothing();
                }
                latch.countDown();
            }).start();
        }
    }
}

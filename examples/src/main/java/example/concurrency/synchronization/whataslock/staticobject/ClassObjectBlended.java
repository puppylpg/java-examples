package example.concurrency.synchronization.whataslock.staticobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 事实证明，无论是把class对象作为锁，把class作为锁，还是把static method作为锁，都是把class对象作为锁。
 *
 * @author liuhaibo on 2022/05/18
 */
public class ClassObjectBlended extends LockBase {

    public void lockClassObject() {
        synchronized (this.getClass()) {
            work();
        }
    }

    public void lockClassObject2() {
        synchronized (ClassObjectBlended.class) {
            work();
        }
    }

    public static synchronized void lockStaticMethod() {
        staticWork();
    }

    public void lockNothing() {
        work();
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(12);
        System.out.println("They all lock the same object...");
        lockSameObject(latch);
        latch.await();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:47:25 CST 2022
     * 当前执行线程:Thread-11,执行时间:Thu May 19 10:47:26 CST 2022
     * 当前执行线程:Thread-10,执行时间:Thu May 19 10:47:27 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 10:47:28 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:47:29 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:47:30 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:47:31 CST 2022
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:47:32 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:47:33 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:47:34 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:47:35 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:47:36 CST 2022
     */
    public static void lockSameObject(CountDownLatch latch) {
        ClassObjectBlended classObjectBlended = new ClassObjectBlended();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                classObjectBlended.lockClassObject();
                latch.countDown();
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                classObjectBlended.lockClassObject2();
                latch.countDown();
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                ClassObjectBlended.lockStaticMethod();
                latch.countDown();
            }).start();
        }

        // 显式使用class object作为锁
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                synchronized (ClassObjectBlended.class) {
                    classObjectBlended.lockNothing();
                }
                latch.countDown();
            }).start();
        }
    }
}

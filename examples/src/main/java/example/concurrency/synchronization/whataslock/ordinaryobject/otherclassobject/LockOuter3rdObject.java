package example.concurrency.synchronization.whataslock.ordinaryobject.otherclassobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 使用别的类的对象作为锁，且这个对象是全局独有的，那和使用class object作为锁没区别，大家都是全局独有的。
 *
 * @author puppylpg on 2022/05/18
 */
public class LockOuter3rdObject extends LockBase {

    final Object lock;

    public LockOuter3rdObject(Object lock) {
        this.lock = lock;
    }

    public void method() {
        synchronized (lock) {
            work();
        }
    }

    public static void main(String... args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("lock one object is okay...");
        lockOneObject(latch);
        latch.await();
        System.out.println("lock many objects is also okay...");
        lockManyObject();
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-0,执行时间:Thu May 19 09:52:24 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 09:52:25 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 09:52:26 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 09:52:27 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 09:52:28 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        Object uniqueLock = new Object();
        LockOuter3rdObject lockOuter3rdObject = new LockOuter3rdObject(uniqueLock);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockOuter3rdObject.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 能锁住，一个一个来：
     *
     * 当前执行线程:Thread-5,执行时间:Thu May 19 09:52:29 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 09:52:30 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 09:52:31 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 09:52:32 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 09:52:33 CST 2022
     */
    public static void lockManyObject() {
        Object uniqueLock = new Object();
        for (int i = 0; i < 5; i++) {
            LockOuter3rdObject lockOuter3rdObject = new LockOuter3rdObject(uniqueLock);
            new Thread(lockOuter3rdObject::method).start();
        }
    }
}

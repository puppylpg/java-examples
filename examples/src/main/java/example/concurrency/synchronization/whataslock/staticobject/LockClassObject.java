package example.concurrency.synchronization.whataslock.staticobject;

import example.concurrency.synchronization.whataslock.LockBase;

import java.util.concurrent.CountDownLatch;

/**
 * 把class object作为锁，其实还是用Class对象当锁。
 * <p>
 * 不建议使用这种，因为如果class不是final，子类和父类、子类和子类之间的`this.getClass()`用的都不是同一个class object！
 * 所以建议把使用的class object硬编码进来，比如`LockClassObject.class`，这样就确定用的是同一个对象作为锁。（或者把class设为final）
 * <p>
 * - https://rules.sonarsource.com/java/tag/multi-threading/RSPEC-3067
 *
 * @author puppylpg on 2022/05/18
 */
@Deprecated
public class LockClassObject extends LockBase {

    public void method() {
        synchronized (this.getClass()) {
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
     * <p>
     * 当前执行线程:Thread-0,执行时间:Thu May 19 10:09:51 CST 2022
     * 当前执行线程:Thread-3,执行时间:Thu May 19 10:09:52 CST 2022
     * 当前执行线程:Thread-4,执行时间:Thu May 19 10:09:53 CST 2022
     * 当前执行线程:Thread-2,执行时间:Thu May 19 10:09:54 CST 2022
     * 当前执行线程:Thread-1,执行时间:Thu May 19 10:09:55 CST 2022
     */
    public static void lockOneObject(CountDownLatch latch) {
        LockClassObject lockClassObject = new LockClassObject();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lockClassObject.method();
                latch.countDown();
            }).start();
        }
    }

    /**
     * 能锁住，一个一个来：
     * <p>
     * 当前执行线程:Thread-5,执行时间:Thu May 19 10:09:56 CST 2022
     * 当前执行线程:Thread-9,执行时间:Thu May 19 10:09:57 CST 2022
     * 当前执行线程:Thread-8,执行时间:Thu May 19 10:09:58 CST 2022
     * 当前执行线程:Thread-7,执行时间:Thu May 19 10:09:59 CST 2022
     * 当前执行线程:Thread-6,执行时间:Thu May 19 10:10:00 CST 2022
     */
    public static void lockManyObject() {
        for (int i = 0; i < 5; i++) {
            LockClassObject lockClassObject = new LockClassObject();
            new Thread(lockClassObject::method).start();
        }
    }
}

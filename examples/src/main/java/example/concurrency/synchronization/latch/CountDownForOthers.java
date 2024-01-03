package example.concurrency.synchronization.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link CountDownLatch}countdown的和await的不是同一波人，是“一波人给另一波人放行”，或者说“等另一波人凑齐了我再走”。
 * {@link CyclicBarrier}则是同一波人，凑购数了一起冲破barrier继续做后面的事情。是“自己给自己放行”，“凑够一堆过马路”233
 *
 * @author puppylpg on 2022/05/20
 */
public class CountDownForOthers {

    /**
     * Thread-1: 4
     * Thread-3: 7
     * Thread-2: 9
     * Thread-0: 3
     * Thread-4: 1
     * Thread-5: 3
     * Thread-6: 1
     * waiting...
     * Thread-7: 2
     * count: 9
     * Thread Done: Thread-2
     * Thread Done: Thread-3
     * Thread Done: Thread-1
     * Thread Done: Thread-5
     * Thread Done: Thread-0
     * Thread Done: Thread-7
     * Thread Done: Thread-6
     * Thread Done: Thread-4
     * Thread-8: 4
     * Thread Done: Thread-8
     * 你们终于给我放行了...
     */
    public static void main(String... args) throws InterruptedException {
        CountDownForOthers demo = new CountDownForOthers();
        CountDownLatch latch = new CountDownLatch(9);

        int i = 9;
        while (i-- > 0) {
            new Thread(() -> demo.work(latch)).start();
        }
        System.out.println("waiting...");

        showTaskMetrics(latch);
        latch.await();
        System.out.println("你们终于给我放行了...");
    }

    private static void showTaskMetrics(CountDownLatch latch) {
        long count = latch.getCount();
        System.out.printf("count: %s\n", count);
    }

    private void work(CountDownLatch latch) {
        try {
            int time = new Random().nextInt(10);
            System.out.println(Thread.currentThread().getName() + ": " + time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread Done: " + Thread.currentThread().getName());

        latch.countDown();
    }
}

package example.concurrency.synchronization.barrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link java.util.concurrent.CountDownLatch}countdown的和await的不是同一波人，是“一波人给另一波人放行”。
 * {@link CyclicBarrier}则是同一波人，凑购数了一起冲破barrier继续做后面的事情。是“自己给自己放行”，“凑够一堆过马路”233
 *
 * @author liuhaibo on 2022/05/20
 */
public class AutomaticCountDown {

    /**
     * Thread-0: 9
     * Thread-6: 7
     * Thread-3: 3
     * Thread-7: 7
     * Thread-4: 4
     * total: 9, finished: 0
     * cyclic broken? false
     * Thread-5: 5
     * Thread-2: 1
     * Thread-1: 3
     * Thread Done: Thread-0 等人过马路
     * Thread Done: Thread-7 等人过马路
     * Thread Done: Thread-6 等人过马路
     * Thread Done: Thread-4 等人过马路
     * Thread Done: Thread-3 等人过马路
     * Thread-8: 6
     * Thread Done: Thread-2 等人过马路
     * Thread Done: Thread-1 等人过马路
     * Thread Done: Thread-5 等人过马路
     * Thread Done: Thread-8 等人过马路
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     * 凑够一波人了，大家一起冲！
     */
    public static void main(String... args) {
        AutomaticCountDown demo = new AutomaticCountDown();
        CyclicBarrier barrier = new CyclicBarrier(9);

        int i = 9;
        while (i-- > 0) {
            Thread t = new Thread(() -> demo.work(barrier));
//            t.setDaemon(true);
            t.start();
        }
        showTaskMetrics(barrier);
        // 完成任务数+1，并等待所有异步任务结束
        System.out.println("cyclic broken? " + barrier.isBroken());
    }

    private static void showTaskMetrics(CyclicBarrier barrier) {
        int parties = barrier.getParties();
        int numberWaiting = barrier.getNumberWaiting();
        System.out.printf("total: %s, finished: %s\n", parties, numberWaiting);
    }

    private void work(CyclicBarrier barrier) {
        try {
            int time = new Random().nextInt(10);
            System.out.println(Thread.currentThread().getName() + ": " + time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread Done: " + Thread.currentThread().getName() + " 等人过马路");
        // 异步任务结束，完成任务数+1
        try {
            barrier.await();
            System.out.println("凑够一波人了，大家一起冲！");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

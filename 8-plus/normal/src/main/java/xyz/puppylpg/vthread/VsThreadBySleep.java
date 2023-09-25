package xyz.puppylpg.vthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

/**
 * 使用不同的线程池提交blocking task
 *
 * @author liuhaibo on 2023/09/25
 */
public class VsThreadBySleep {
    private static final LongAdder COUNTER = new LongAdder();
    private static final int ITERATION = 100000;

    private static final Runnable BLOCKING_TASK = () -> {
        try {
            Thread.sleep(100);
            // 0.1%
            if (RandomGenerator.getDefault().nextInt(0, ITERATION) <= ITERATION / 1000) {
                System.out.println("I'm running in thread " + Thread.currentThread());
            }
            COUNTER.increment();
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    };

    public static void main(String... args) {

        // I'm running in thread Thread[#585,pool-1-thread-565,5,main]
        // Completed 100000 tasks in 2864ms
        thread(BLOCKING_TASK);

        // I'm running in thread VirtualThread[#107757]/runnable@ForkJoinPool-1-worker-3
        // Completed 200000 tasks in 1516ms
        virtualThread(BLOCKING_TASK);
    }

    // 虽然是线程池，但是本质上，一个任务还是和一个线程强绑定的
    private static void thread(Runnable task) {
        long start = System.nanoTime();
        // try with resource会在最后调用close，而executor的close会在所有已提交任务结束后才关闭线程池，所以并不是提交完任务主程序就结束了
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            IntStream.range(0, ITERATION)
                    .forEach(number -> executorService.submit(task));
        }
        long end = System.nanoTime();
        System.out.println("Completed " + COUNTER.intValue() + " tasks in " + (end - start)/1000000 + "ms");
    }

    // 一个任务和一个虚线程强绑定，和线程不强绑定
    private static void virtualThread(Runnable task) {
        long start = System.nanoTime();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, ITERATION)
                    .forEach(number -> executor.submit(task));
        }
        long end = System.nanoTime();
        System.out.println("Completed " + COUNTER.intValue() + " tasks in " + (end - start)/1000000 + "ms");
    }
}

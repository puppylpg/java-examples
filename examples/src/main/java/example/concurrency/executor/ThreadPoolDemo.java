package example.concurrency.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;

/**
 * 不同的ThreadPool policy
 *
 * @author puppylpg on 2021/10/15
 */
public class ThreadPoolDemo {

    private static final int TASK_NUM = 20;

    private static Callable<String> createCallableTask(String taskName) {
        return () -> {
            int time = RandomUtils.nextInt(500, 1000);
            TimeUnit.MILLISECONDS.sleep(time);
            print("Finished: " + taskName + " time=" + time);
            return taskName;
        };
    }

    private static void doAnotherJob() throws InterruptedException {
        Thread.sleep(500);
    }

    public static void main(String... args) throws InterruptedException {
        RejectedExecutionHandler callerBlocksPolicy = (r, executor) -> {
            try {
                // 不建议直接操作这个内部queue：Access to the task queue is intended primarily for debugging and monitoring.
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 此时，这个线程池就等于生产者消费者模型里的：阻塞队列 + 消费者
        ExecutorService executor = new ThreadPoolExecutor(
                2,
                4,
                20L,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("sub process" + "-%d").setDaemon(false).build(),
                callerBlocksPolicy
        );

        // 提交一堆并行任务，最好是和CPU无关的，比如I/O密集型的下载任务
        for (int i = 1; i <= TASK_NUM; i++) {
            String taskName = "Hello => " + i;
            print("try to submit task: " + taskName);
            executor.submit(createCallableTask(taskName));
            print("submit task: " + taskName);
        }

        print(executor.toString());
    }

    private static void print(String str) {
        System.out.println(Thread.currentThread().getName() + ": " + str);
    }
}

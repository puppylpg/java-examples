package example.concurrency.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.*;

/**
 * 提交一大堆并行任务，然后使用{@link CompletionService}及时获取{@link Future}结果。
 * 避免了轮询。
 *
 * @author liuhaibo on 2021/10/15
 */
public class FakeThreadPoolDemo {

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
        ExecutorService executor = new ThreadPoolExecutor(
                2,
                4,
                20L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("sub process" + "-%d").setDaemon(false).build()
        );

        BlockingQueue<Callable<String>> externalBq = new ArrayBlockingQueue<>(10);

//        Runnable runnable = () -> {
//            while (true) {
//                Callable<String> task = null;
//                try {
//                    task = externalBq.take();
//                    print("take task: " + task);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                executor.submit(task);
//            }
//        };

//        new Thread(runnable).start();

        executor.execute(new Consumer(externalBq));

        // 提交一堆并行任务，最好是和CPU无关的，比如I/O密集型的下载任务
        for (int i = 1; i <= TASK_NUM; i++) {
            String taskName = "Hello => " + i;
            print("submit task: " + taskName);
            externalBq.put(createCallableTask(taskName));
        }

        // 同时并行做一些耗时的任务
        doAnotherJob();

        print(executor.toString());
    }

    private static void print(String str) {
        System.out.println(Thread.currentThread().getName() + ": " + str);
    }


    public static class Consumer implements Runnable {


        BlockingQueue<Callable<String>> blockingQueue;
        Consumer(BlockingQueue<Callable<String>> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                Callable<String> task = blockingQueue.take();

                task.call();
            }
        }
    }

}

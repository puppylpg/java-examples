package example.concurrency.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 提交一大堆并行任务，然后使用{@link CompletionService}及时获取{@link Future}结果。
 * 避免了轮询。
 *
 * @author puppylpg on 2018/06/13
 */
public class CompletionServiceDemo {

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
        Executor executor = new ThreadPoolExecutor(
                2,
                4,
                20L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("sub process" + "-%d").setDaemon(true).build(),
                new ThreadPoolExecutor.AbortPolicy());

        CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

        List<Future<String>> futureResults = new ArrayList<>();

        // 提交一堆并行任务，最好是和CPU无关的，比如I/O密集型的下载任务
        for (int i = 1; i <= TASK_NUM; i++) {
            String taskName = "Hello => " + i;
            print("try to submit task: " + taskName);
            Future<String> future = completionService.submit(createCallableTask(taskName));
            print("submit task: " + taskName);
            futureResults.add(future);
        }

        // 同时并行做一些耗时的任务
        doAnotherJob();

        // 使用CompletionService，去BlockingQueue中取一些已完成的任务
        // 另一种低级实现方法：保留每个任务关联的Future，并反复调用future.get(timeout=0)去轮询，获取任务完成情况
        // 或者使用invokeAll，获取返回的List<Future<T>>，然后反复调用future.get(timeout=0)去轮询
        try {
            while (futureResults.size() > 0) {
                Future<String> future = completionService.take();
                String s = future.get();
                print("Get result: " + s);
                futureResults.remove(future);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        print(executor.toString());
    }

    private static void print(String str) {
        System.out.println(Thread.currentThread().getName() + ": " + str);
    }
}

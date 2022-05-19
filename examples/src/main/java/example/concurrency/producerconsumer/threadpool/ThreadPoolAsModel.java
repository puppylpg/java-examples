package example.concurrency.producerconsumer.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author puppylpg on 2021/10/17
 */
public class ThreadPoolAsModel {

    public static void main(String [] args) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(
                2,
                4,
                20L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadFactoryBuilder().setNameFormat("sub process" + "-%d").setDaemon(true).build()
        );

        System.out.println("producer start at " + System.currentTimeMillis());
        int i = 0;
        while (i++ < 20) {

            int finalI = i;
            executor.submit(() -> {
                System.out.println("<= pop <= " + Thread.currentThread().getName() + " : " + finalI);
            });
            System.out.println("=> push => " + Thread.currentThread().getName() + " : " + finalI);
        }
        System.out.println("+++++ EXIT!( producer ) +++++");

        Thread.sleep(3 * 1000);
    }
}

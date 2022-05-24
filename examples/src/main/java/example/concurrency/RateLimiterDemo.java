package example.concurrency;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author puppylpg on 2022/05/24
 */
public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {
        //新建一个每秒限制2个的令牌桶
        RateLimiter rateLimiter = RateLimiter.create(2.0);
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                //获取令牌桶中一个令牌，最多等待10秒
                if (rateLimiter.tryAcquire(1, 10, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName()+" "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
            });
        }

        executor.shutdown();
    }
}

package example.concurrency.executor.parallel.worker;

import example.concurrency.executor.parallel.ArecContext;

/**
 * @author benzhu on 2024/09/16
 */
public abstract class ArecWorker {

    public void work(ArecContext context, String uid) {
        long threadId = Thread.currentThread().threadId();

        // first, cas to get query number
        getQueryNo(context, uid, threadId);
        // second, cache
        getQueryNo(context, uid, threadId);
    }

    private void getQueryNo(ArecContext context, String uid, long threadId) {
        long start = System.currentTimeMillis();
        int queryNo = context.workerQueryNo(threadId, workerType());
        long end = System.currentTimeMillis();

        System.out.printf("%s -> %s, %sms %n", threadId, queryNo + "_" + uid, end - start);
    }

    public abstract String workerType();
}

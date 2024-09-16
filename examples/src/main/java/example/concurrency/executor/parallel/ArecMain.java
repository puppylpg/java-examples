package example.concurrency.executor.parallel;

import static example.concurrency.executor.parallel.ArecContext.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import example.concurrency.executor.parallel.worker.ArecWorker;
import example.concurrency.executor.parallel.worker.TermWorker;
import example.concurrency.executor.parallel.worker.VectorWorker;

/**
 * @author benzhu on 2024/09/16
 */
public class ArecMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArecMain main = new ArecMain();

        ArecWorker termWorker = new TermWorker();
        main.runParallel(termWorker);
        main.runSingle(termWorker);

        ArecWorker vectorWorker = new VectorWorker();
        main.runSingle(vectorWorker);
        main.runParallel(vectorWorker);
    }

    public void runParallel(ArecWorker arecWorker) throws ExecutionException, InterruptedException {
        System.out.println("Starting Parallel Worker...");

        ExecutorService executor = Executors.newFixedThreadPool(8);

        // 4 valid query for this worker to run, here are there positions
        List<AtomicLong> queries = Stream.of(VALID_QUERY, INVALID_QUERY, VALID_QUERY, VALID_QUERY, INVALID_QUERY, VALID_QUERY).map(AtomicLong::new).toList();

        ArecContext arecContext = new ArecContext();
        arecContext.setQueryPosList(queries, arecWorker.workerType());

        long tasks = queries.stream().filter(i -> i.get() == VALID_QUERY).count();

        List<Future<Void>> futures = new ArrayList<>();
        for (long i = 0; i < tasks; i++) {
            Future<Void> result = executor.submit(() -> {
                arecWorker.work(arecContext, "parallel");
                return null;
            });
            futures.add(result);
        }

        for (Future<Void> result : futures) {
            result.get();
        }

        executor.shutdown();
    }

    public void runSingle(ArecWorker arecWorker) {
        System.out.println("Starting Single Worker...");

        ArecContext arecContext = new ArecContext();

        arecWorker.work(arecContext, "single");
    }
}

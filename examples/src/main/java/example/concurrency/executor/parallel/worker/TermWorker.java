package example.concurrency.executor.parallel.worker;

/**
 * @author benzhu on 2024/09/16
 */
public class TermWorker extends ArecWorker {
    @Override
    public String workerType() {
        return "term";
    }
}

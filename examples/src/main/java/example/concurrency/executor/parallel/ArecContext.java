package example.concurrency.executor.parallel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author benzhu on 2024/09/16
 */
public class ArecContext {
    public static final int VALID_QUERY = -1;
    public static final int INVALID_QUERY = -2;

    public Map<String, Object> attributes = new HashMap<>();


    public List<AtomicLong> queryPosList(String workerType) {
        return (List<AtomicLong>) attributes.getOrDefault(queryPosKey(workerType), new ArrayList<>());
    }

    public void setQueryPosList(List<AtomicLong> queryPosList, String workerType) {
        attributes.put(queryPosKey(workerType), queryPosList);
    }

    public String workerQueryKey(long threadId, String workerType) {
        return threadId + workerType + "_query";
    }

    public String queryPosKey(String workerType) {
        return workerType + "_query_pos";
    }

    public int workerQueryNo(long threadId, String workerType) {
        String key = workerQueryKey(threadId, workerType);
        // cache
        if (attributes.containsKey(key)) {
            return (int) attributes.get(key);
        }

        List<AtomicLong> queries = queryPosList(workerType);
        // not a concurrent worker invoke
        if (queries.isEmpty()) {
            return -1;
        }

        // concurrent worker invoke, try to fetch a query
        for (int i = 0; i < queries.size(); i++) {
            AtomicLong query = queries.get(i);
            if (query.get() == threadId) {
                attributes.put(key, i);
                return i;
            } else {
                if (query.get() == VALID_QUERY) {
                    boolean result = query.compareAndSet(VALID_QUERY, threadId);
                    if (result) {
                        attributes.put(key, i);
                        return i;
                    }

                }
            }
        }

        return INVALID_QUERY;
    }
}

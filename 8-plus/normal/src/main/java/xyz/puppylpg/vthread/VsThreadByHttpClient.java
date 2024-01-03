package xyz.puppylpg.vthread;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * 虚线程的意义在于：使用虚线程（可以是直接new，也可以是虚线程池，当然我们倾向于线程池）跑blocking任务更高效，所以不必使用reactive框架继续分解任务了。
 * 并不是不需要异步提交了，毕竟使用线程池就需要异步提交。
 *
 * @author puppylpg on 2023/09/25
 */
public class VsThreadByHttpClient {

    private static final String URI_TEMPLATE = "https://postman-echo.com/get?loop=%d";
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private static final ExecutorService V_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    public static void main(String... args) throws URISyntaxException {
        // async request, total time={4604}ms
        sendAsync(EXECUTOR);
        // async request, total time={2711}ms
        sendAsync(V_EXECUTOR);
    }

    private static void sendAsync(ExecutorService executorService) throws URISyntaxException {
        long start = System.currentTimeMillis();
        var responses = new ArrayList<>();
        // 记得关闭
        try (HttpClient httpClient = HttpClient.newBuilder()
                .executor(executorService)
                .build() ) {
            for (int i = 0; i < 1000; i++) {
                CompletableFuture<HttpResponse<String>> response = httpClient
                        .sendAsync(
                                HttpRequest.newBuilder()
                                        .timeout(Duration.of(10, SECONDS))
                                        .GET()
                                        .uri(new URI(URI_TEMPLATE.formatted(i))
                                        ).build(),
                                HttpResponse.BodyHandlers.ofString()
                        );
                responses.add(response);
            }
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(responses.toArray(new CompletableFuture[0]));
            allFutures.join();
            System.out.printf("async request, total time={%d}ms%n", System.currentTimeMillis() - start);
        }
    }
}

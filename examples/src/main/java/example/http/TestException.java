package example.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liuhaibo on 2022/12/06
 */
public class TestException {

    public static void main(String... args) throws InterruptedException, IOException {
        CloseableHttpAsyncClient httpclient = HttpAsyncClientBuilder.create()
                .setDefaultIOReactorConfig(
                        IOReactorConfig.custom().build()
                )
                .build();

        try {

            httpclient.start();

            AtomicBoolean fireException = new AtomicBoolean(false);

            while (true) {

                System.out.println("client running? " + httpclient.isRunning());

                try {

                    String url;

                    if (fireException.compareAndSet(false, true)) {

                        url = "http://localhost:80181"; // throw Connection refused

                    } else {

                        url = "http://www.apache.org/";

                    }

                    final HttpGet request2 = new HttpGet(url);
                    System.out.println(request2);

                    httpclient.execute(request2, new FutureCallback<HttpResponse>() {

                        public void completed(final HttpResponse response2) {

                            System.out.println("completed, " + request2.getRequestLine() + "->" + response2.getStatusLine());

                        }

                        public void failed(final Exception ex) {

                            System.out.println("failed, " + request2.getRequestLine() + "->" + ex);

                            throw new RuntimeException();

                        }

                        public void cancelled() {

                            System.out.println(request2.getRequestLine() + " cancelled");

                        }

                    });

                    TimeUnit.SECONDS.sleep(1);

                } catch (Exception e) {

                    e.printStackTrace();

                    TimeUnit.SECONDS.sleep(1);

                }

            }

        } finally {

            httpclient.close();

        }
    }
}

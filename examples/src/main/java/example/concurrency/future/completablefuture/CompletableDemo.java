package example.concurrency.future.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author puppylpg on 2020/06/04
 */
public class CompletableDemo {

    public static void main(String... args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> "Hello");
//        CompletableFuture<String> world = CompletableFuture.completedFuture("World");
//        CompletableFuture<String> helloWorld1 = hello.thenApplyAsync(old -> old + "World");
//        CompletableFuture<String> helloWorld2 = hello.thenComposeAsync(old -> CompletableFuture.supplyAsync(() -> old + "World"));
//        CompletableFuture<String> helloWorld3 = hello.thenCombineAsync(world, (old, newValue) -> old + newValue);
//
//        helloWorld1.thenAccept(value -> System.out.println("HelloWorld1 then accept: " + value));
//        helloWorld2.thenAcceptAsync(value -> System.out.println("HelloWorld2 then accept async: " + value));
//        helloWorld3.thenAcceptAsync(value -> System.out.println("HelloWorld3 then accept async: " + value))
//                .thenRunAsync(() -> System.out.println("HelloWorld3 then run async"));
//
//        // ???
//        CompletableFuture<Void> allResult = CompletableFuture.allOf(hello, world);
//        // null
//        System.out.println(allResult.get());
//        CompletableFuture<Object> anyResult = CompletableFuture.anyOf(hello, world);
//        System.out.println(anyResult.get());
//
//        CompletableFuture<String> handleResult = hello.handle((value, exception) -> value == null ? exception.getMessage() : value);
//        System.out.println(handleResult.get());

        CompletableFuture<String> a = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("a");
            return "a";
        });
        CompletableFuture<String> b = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("b");
            return "b";
        });
        CompletableFuture.allOf(a, b).thenAccept(result -> {
            System.out.println(result + "c");
        });
        Thread.sleep(100000);
    }
}

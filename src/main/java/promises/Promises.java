package promises;

import java.util.concurrent.CompletableFuture;

public class Promises {
  public static void delay() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) throws Throwable {
    var cf = CompletableFuture.supplyAsync(
        () -> {
          System.out.println(Thread.currentThread().getName()
          + " supplying value");
          return "Hello!";
        }
    )
        .thenApplyAsync(s -> {
          System.out.println(Thread.currentThread().getName()
              + " processing " + s);
          delay();
          return s.toUpperCase();
        });
    System.out.println("initial build of pipeline complete");
//    var cf2 =
//        cf.thenAcceptAsync(s -> System.out.println());
//    var cf3 =
//        cf.thenApplyAsync(s -> "Hello " + s.toLowerCase())
    System.out.println("final handler added to pipeline");
//    cf.join();
//    cf2.join();

    System.out.println("cf has completed");
    Thread.sleep(1000); // it's PUSH based, not PULL based. No need for
    // a specific "terminal operation" (unlike streams)

    // other things to know
    // there actually 2 "data paths" between elements
    // one is "succesful data" other is unchecked exceptions :)
    // look for handle/handleAsync, these take BiFunctions
    // first arg is successful data, second (alternate) is a Throwable

    // for normal "promise" you can provide asynchronous methods...
    // such methods return a promise...
    // this happens in CompletableFuture, but a) the async method
    // returns a CompletableFuture, and b) the only method that
    // does this (at the caller) is "themCompose" (this is the
    // monadic "flatMap" method!!!
  }
}

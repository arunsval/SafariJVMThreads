package badqueue;

class BadQueue<E> {
  private static final int SIZE = 10;
  private E[] data = (E[])new Object[SIZE];
  private int count = 0;
  private Object rendezvous = new Object();

  public void put(E e) throws InterruptedException {
    synchronized (this.rendezvous) {
      // what if the queue is full already??
      // many lines of transactional problem!!!
      while (count == SIZE) { // MUST have a loop here (always)
        // hang about??? MUST give the key back
        // AND THEN RECOVER THE KEY before continuing
        // CAREFUL!! NOT transactionally protected DURING the wait!!!
        this.rendezvous.wait();
      }
      data[count++] = e;
      this.rendezvous.notify();
    }
  }

  public E take() throws InterruptedException {
    synchronized (this.rendezvous) {
      // what if there's nothing there!?
      // many lines of transactional problem
      while (count == 0) {
        // hang about
        this.rendezvous.wait();
      }
      E result = data[0];
      System.arraycopy(data, 1, data, 0, --count);
      this.rendezvous.notify();
      return result;
    }
  }
}

public class UseBadQueue {
  public static void main(String[] args) {



//    BadQueue<Integer> bqi = new BadQueue<>();
//    bqi.put(1);
//    bqi.put(2);
//    System.out.println(bqi.take());
//    bqi.put(3);
//    System.out.println(bqi.take());
//    System.out.println(bqi.take());

  }
}

package idme;

/**
 * Thread-safe incrementing counter.
 *
 * NOTE - I'm not entirely sure that I need this.  '++foo' may atomic,
 * but I figure since this may one day be a synchronized-hash, it'll
 * matter more then.
 *
 * Also, this'll eventually fail when numbers get too big, since I'm
 * just using `ints`.
 *
 * @author Adam Wagner
 */
public class SynchronizedCounter {

    private int count = 0;

    public SynchronizedCounter(int startAt) {
        count = startAt;
    }

    public synchronized int next() {
        return ++count;
    }

    public synchronized int current() {
        return count;
    }

}


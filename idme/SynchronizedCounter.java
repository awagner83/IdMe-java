package idme;

import java.util.*;

/**
 * Thread-safe incrementing counter.
 *
 * Note - this'll eventually fail when numbers get too big, since I'm
 * just using `ints`.
 *
 * @author Adam Wagner
 */
public class SynchronizedCounter {

    private List<Callback> callbacks = new LinkedList<Callback>();
    private int count;
    private int nextCheckpoint;
    private final int stepSize;

    public SynchronizedCounter(int startAt) {
        this(startAt, 100);     // Default step-size to 100
    }

    public SynchronizedCounter(int startAt, int stepSize) {
        count = startAt;
        nextCheckpoint = startAt + stepSize;
        this.stepSize = stepSize;
    }

    public synchronized int next() {
        if (++count > nextCheckpoint) {
            nextCheckpoint += stepSize;
            runCallbacks();
        }
        return count;
    }

    public synchronized int current() {
        return count;
    }

    public synchronized String statusString() {
        return String.format("Current: %s; Next Checkpoint: %s",
                count, nextCheckpoint);
    }

    public void registerCallback(Callback cb) {
        callbacks.add(cb);
    }

    private void runCallbacks() {
        Iterator<Callback> callbackIter = callbacks.iterator();
        while (callbackIter.hasNext()) {
            callbackIter.next().run(count, nextCheckpoint);
        }
    }

}

